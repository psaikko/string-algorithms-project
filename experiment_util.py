from subprocess import Popen, PIPE
import subprocess
import re

no_console = subprocess.STARTUPINFO()
no_console.dwFlags |= subprocess.STARTF_USESHOWWINDOW

time_regex = re.compile(r"Preprocessing: (\d+) ms.*Searching: (\d+) ms", flags=re.DOTALL)

def time_algorithm(algorithm, text, N, pattern_count, pattern_length):
    process = Popen(['java', '-cp', r'.\out', 'Main',
                     text, str(N), str(pattern_count), str(pattern_length), algorithm],
                     stdout=PIPE, stderr=PIPE, startupinfo=no_console)
    stdout, stderr = process.communicate()
    output = str(stdout, 'ascii')
    times = time_regex.match(output).group(1,2)
    return int(times[0]), int(times[1])