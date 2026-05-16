import os
from lib.artifact import ArtifactFiller

def main():
  required_vars = {
    'POSTGRES_HOST': os.environ.get('POSTGRES_HOST'),
    'POSTGRES_DB': os.environ.get('POSTGRES_DB'),
    'POSTGRES_PORT': os.environ.get('POSTGRES_PORT'),
    'POSTGRES_USER': os.environ.get('POSTGRES_USER'),
    'POSTGRES_PASSWORD': os.environ.get('POSTGRES_PASSWORD'),
  }

  missing_vars = [var for var, value in required_vars.items() if value is None]

  if missing_vars:
    raise EnvironmentError(f"Missing required environment variables: {', '.join(missing_vars)}")

  postgres_host: str = required_vars['POSTGRES_HOST']  # type: ignore
  postgres_db: str = required_vars['POSTGRES_DB']  # type: ignore
  postgres_port: str = required_vars['POSTGRES_PORT']  # type: ignore
  postgres_user: str = required_vars['POSTGRES_USER']  # type: ignore
  postgres_password: str = required_vars['POSTGRES_PASSWORD']  # type: ignore

  context = {
    'POSTGRES_HOST': postgres_host,
    'POSTGRES_DB': postgres_db,
    'POSTGRES_PORT': postgres_port,
    'POSTGRES_USER': postgres_user,
    'POSTGRES_PASSWORD': postgres_password,
  }

  java_opts = os.environ.get('JAVA_OPTS')
  if java_opts:
    context['JAVA_OPTS'] = java_opts

  artifact_filler = ArtifactFiller(context, ['./src', './report'])
  artifact_filler.compile_patterns()

if __name__ == "__main__":
  main()