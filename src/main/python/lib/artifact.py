import os
from typing import Dict, List
from jinja2 import FileSystemLoader, meta, Environment

class ArtifactFiller:
  def __init__(self, context: Dict[str, str], pattern_dirs: List[str]):
    self.context: Dict[str, str] = context
    self.pattern_dirs = pattern_dirs

  def compile_patterns(self):
    for path in self.pattern_dirs:
      if not os.path.isdir(path):
        raise NotADirectoryError(f"Invalid report directory: {path}")

      env = Environment(loader=FileSystemLoader(path))

      for root, _, files in os.walk(path):
        for filename in files:
          if filename.endswith(".jinja"):
            rel_path = os.path.relpath(os.path.join(root, filename), path)
            base, ext, _ = os.path.join(path, rel_path).rsplit('.', 2)
            output_path = base + '.compiled.' + ext
            
            rendered_content = self._render_template(env, rel_path)
            
            os.makedirs(os.path.dirname(output_path), exist_ok=True)
            with open(output_path, "w") as f:
              f.write(rendered_content)

  def _render_template(self, env: Environment, template_name: str) -> str:
    if env.loader is None:
      raise RuntimeError("Jinja loader not initialized.")
    template_source, _, _ = env.loader.get_source(env, template_name)
    ast = env.parse(template_source)
    required_vars = meta.find_undeclared_variables(ast)

    render_kwargs = {v: self.context.get(v, "") for v in required_vars}
    
    template = env.get_template(template_name)
    return template.render(**render_kwargs)