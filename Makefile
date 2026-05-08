include build.properties

ignored_files=$$( \
	echo .git; \
	echo .github; \
	git check-ignore -- * \
)

sandbox_remote=$(fse.svn.sandbox.dir)/$(fse.svn.sandbox.remote)
sandbox_remote_abs=$$(realpath $(sandbox_remote))
sandbox_local=$(fse.svn.sandbox.dir)/$(fse.svn.sandbox.local)
sandbox_local_abs=$$(realpath $(sandbox_local))
ant_home = $(sandbox_local)/$(fse.ant.home)

image_dev = "test"

clean:
	# clean LaTeX artifacts
	@for file_ext in "*.pdf" "*.aux" "*.log" "*.toc" "*.out"; do \
		find $(fse.pdf.report_dir) -name "$${file_ext}" -delete; \
	done
	# clean other artifacts
	find . -name "*.compiled.*" -delete; \
	rm -rf target $(fse.svn.sandbox.dir)
run: clean
	python src/main/python/main.py
	mvn package
	docker compose --file docker/compose.yml --project-directory . up
down:
	docker compose --file docker/compose.yml --project-directory . down
sandbox: clean
	@echo "# creating sandbox..."
	@mkdir -p $(sandbox_remote) $(sandbox_local) $(ant_home)
	@exclude_args=""; \
	for pattern in $(ignored_files); do \
		exclude_args="$$exclude_args -not -path ./$$pattern"; \
	done; \
	find . -maxdepth 1 -mindepth 1 $$exclude_args -exec cp -r "{}" $(sandbox_local) \;
	@echo "# initializing SVN remote repository..."
	svnadmin create $(sandbox_remote_abs)
	svn mkdir file://$(sandbox_remote_abs)/trunk -m "create 'trunk' directory"
	@echo "# initializing SVN local repository..."
	svn checkout file://$(sandbox_remote_abs)/trunk $(sandbox_local)
	svn add --force $(sandbox_local)
	svn commit -m "chore: init commit" $(sandbox_local)
	@echo "# copying Ant depedencies for the first time"
	cp -r /ant/lib $(ant_home)
	@echo "# svn sandbox is done!"
pdf:
	cd "${fse.pdf.report_dir}"; \
	xelatex --shell-escape -interaction=nonstopmode "${fse.pdf.report_name}"; \
	xelatex --shell-escape -interaction=nonstopmode "${fse.pdf.report_name}"