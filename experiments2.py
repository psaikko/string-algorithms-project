from subprocess import Popen, PIPE
import subprocess
import matplotlib.pyplot as plt
import re
import numpy as np

min_patterns = 1
min_length = 5
max_patterns = 1000
max_length = 1000
granularity = 30

no_console = subprocess.STARTUPINFO()
no_console.dwFlags |= subprocess.STARTF_USESHOWWINDOW
time_regex = re.compile(r"Preprocessing: (\d+) ms.*Searching: (\d+) ms", flags=re.DOTALL)

def f(file, N, pattern_count, pattern_length, algorithm):
    process = Popen(['java', '-cp', r'.\out', 'Main',
                     file, str(N),
                     str(pattern_count), str(pattern_length), algorithm],
                    stdout=PIPE, stderr=PIPE,
                    startupinfo=no_console)
    stdout, stderr = process.communicate()
    output = str(stdout, 'ascii')
    str_times = time_regex.match(output).group(1,2)
    return int(str_times[0]), int(str_times[1])

algorithms = ['KarpRabin','AhoCorasick','ShiftAnd']
data_files = ['.\\data\\dblp.xml.200MB','.\\data\\dna.200MB','.\\data\\english.200MB',
              '.\\data\\proteins.200MB','.\\data\\sources.200MB', 'Random2', 'Random256']
data_types = ['XML', 'DNA', 'English', 'Proteins', 'Code', 'Random2', 'Random256']

for i in [0,1,2]:
    algo = algorithms[i];
    preprocessing = []
    search = []
    times = 10
    
    for (j, file) in enumerate(data_files):
        print(algo + ' ' + file)
        tmp1, tmp2 = 0, 0
        for t in range(times):
            a, b = f(file, 200000, 32, 32, algo)
            tmp1 += a
            tmp2 += b
        preprocessing.append(tmp1 / times)
        search.append(tmp2 / times)
    ax = plt.subplot(1,3,i+1)
    plt.setp(plt.xticks()[1], rotation=30)
    ind = np.arange(len(data_files))
    width = 0.9
    plt.bar(ind, preprocessing, color='g')
    plt.bar(ind, search, bottom=preprocessing)
    plt.title(algo)
    ax.set_xticks(ind)
    ax.set_xticklabels(data_types)
plt.show()
