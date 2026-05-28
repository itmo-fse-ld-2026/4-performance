include build.properties

ant_home = $(sandbox_local)$(fse.ant.home)
image_web = $$(cat docker/IMAGE_WEB)

clean:
	# clean LaTeX artifacts
	@for file_ext in "*.pdf" "*.aux" "*.log" "*.toc" "*.out" "*.blg" "*.run.xml" "*.bcf" "*.bbl"; do \
		find $(fse.pdf.report_dir) -name "$${file_ext}" -delete; \
	done
	# clean other artifacts
	find . -name "*.compiled.*" -delete
run: clean
	python src/main/python/main.py
	IMAGE_WEB=$(image_web) docker compose --file docker/compose.yml --project-directory . up
down:
	docker compose --file docker/compose.yml --project-directory . down
pdf:
	cd "${fse.pdf.report_dir}"; \
	xelatex --shell-escape -interaction=nonstopmode "${fse.pdf.report_name}"; \
	biber "${fse.pdf.report_name}"; \
	xelatex --shell-escape -interaction=nonstopmode "${fse.pdf.report_name}"