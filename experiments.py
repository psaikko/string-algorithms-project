from subprocess import Popen, PIPE
import subprocess
import matplotlib.pyplot as plt
import re
import numpy as np
from mpl_toolkits.mplot3d.axes3d import Axes3D

min_patterns = 1
min_length = 5
max_patterns = 1000
max_length = 1000
granularity = 30

no_console = subprocess.STARTUPINFO()
no_console.dwFlags |= subprocess.STARTF_USESHOWWINDOW

def f(pattern_count, pattern_length):
    process = Popen(['java', '-jar', r'.\out\StringProcessingProject.jar',
                     r'.\data\english.200MB', '100000', str(pattern_count), str(pattern_length), 'AhoCorasick'],
                    stdout=PIPE, stderr=PIPE,
                    startupinfo=no_console)
    stdout, stderr = process.communicate()
    output = str(stdout, 'ascii')
    m = re.match(r"Preprocessing: (\d+).*Searching: (\d+)", output, flags=re.DOTALL)
    outs = m.group(1,2)
    time = int(outs[0]) + int(outs[1])
    return time / 1000000

times = []
pattern_counts = np.arange(min_patterns,max_patterns,granularity)
pattern_lengths = np.arange(min_patterns,max_length,granularity)

pattern_counts, pattern_lengths = np.meshgrid(pattern_counts, pattern_lengths)
times = np.array([f(x,y) for x,y in zip(np.ravel(pattern_counts), np.ravel(pattern_lengths))])
times = times.reshape(pattern_counts.shape)

fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')
ax.plot_surface(pattern_counts, pattern_lengths, times, rstride=4, cstride=4, alpha=0.25)

ax.set_ylim3d(min_patterns,max_patterns)
ax.set_xlim3d(max_length,min_length) 

ax.set_xlabel("Number of Patterns")
ax.set_ylabel("Pattern Length")
ax.set_zlabel("Time (ms)")

plt.show()
