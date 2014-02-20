from subprocess import Popen, PIPE
import subprocess
import matplotlib.pyplot as plt
import re
import numpy as np
from mpl_toolkits.mplot3d.axes3d import Axes3D

min_patterns = 20
min_length = 20
max_patterns = 500
max_length = 500
granularity = 40

no_console = subprocess.STARTUPINFO()
no_console.dwFlags |= subprocess.STARTF_USESHOWWINDOW

def f(pattern_count, pattern_length, algorithm):
    process = Popen(['java', '-cp', r'.\out', 'Main',
                     r'.\data\english.200MB', '100000',
                     str(pattern_count), str(pattern_length), algorithm],
                    stdout=PIPE, stderr=PIPE,
                    startupinfo=no_console)
    stdout, stderr = process.communicate()
    output = str(stdout, 'ascii')
    m = re.match(r"Preprocessing: (\d+).*Searching: (\d+)", output, flags=re.DOTALL)
    outs = m.group(1,2)
    time = int(outs[0]) + int(outs[1])
    return time

algorithms = ['KarpRabin','AhoCorasick','ShiftAnd']
fig = plt.figure()
for i in [0,1,2]:
    algo = algorithms[i];
    times = []
    pattern_counts = np.arange(min_patterns,max_patterns,granularity)
    pattern_lengths = np.arange(min_patterns,max_length,granularity)

    pattern_counts, pattern_lengths = np.meshgrid(pattern_counts, pattern_lengths)
    times = np.array([f(x,y,algo) for x,y in zip(np.ravel(pattern_counts), np.ravel(pattern_lengths))])
    times = times.reshape(pattern_counts.shape)

    ax = fig.add_subplot(131+i, projection='3d')
    ax.plot_surface(pattern_counts, pattern_lengths, times, rstride=2, cstride=2, alpha=0.25)

    ax.set_ylim3d(min_patterns,max_patterns)
    ax.set_xlim3d(max_length,min_length) 

    ax.set_xlabel("Number of Patterns")
    ax.set_ylabel("Pattern Length")
    ax.set_zlabel("Time (ms)")
    ax.set_title(algo)
plt.show()
