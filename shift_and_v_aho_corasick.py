from experiment_util import time_algorithm
from matplotlib.pyplot import *
import numpy as np

algos = ["ShiftAnd","AhoCorasick"]
labels = ["Shift-And","Aho-Corasick"]
colors = ['r','g']

total = []
reps = 10

min_patterns = 1
max_patterns = 60
pattern_length = 10
pattern_counts = np.arange(min_patterns,max_patterns+2,2)

for (i, algo) in enumerate(algos):
    times = []
    for pattern_count in pattern_counts:
        print(algo+" "+str(pattern_count))
        tmp = 0; 
        for _ in range(reps):  
            pre, search = time_algorithm(algo, 'Random256', 1000000, pattern_count, pattern_length)
            tmp += pre + search
        times.append(tmp / reps)
    plot(pattern_counts*pattern_length, times, color=colors[i],label=labels[i])
    ylabel('time (ms)')
    xlabel(r'$||\mathcal{P}\,||$')
xlim(min_patterns*pattern_length, max_patterns*pattern_length)
legend(loc='upper left')
savefig('img\\SAvAC.pdf')
