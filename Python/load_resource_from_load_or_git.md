```
import pkgutil
import bz2
import json
import subprocess
import io
import tarfile

def biobank_data_json():
    return _json("biobank_data.json.bz2")

def _load_json(filename):
    """ Loading data resource from project directory
    """
    return json.loads(bz2.decompress(pkgutil.get_data(__package__, filename)))


def biobank_data_json_from_git():
    return _git_db('origin/git_store', 'the_root_dir_in_git/biobank_data.json')


def _git_db(git_version, file_path):
    """
    Utils to use git branch as storage in order to keep blob away from develop branch
    :param git_version: HEAD, origin/develop, etc
    :param file_path: root path from .git
    :return:
    """
    tf = tarfile.open(fileobj=io.BytesIO(subprocess.check_output("git archive".split(" ")+[git_version, file_path])))
    return json.load(tf.extractfile([x for x in tf.getmembers() if x.isfile()][0]))

