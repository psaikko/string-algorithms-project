from experiment_util import time_algorithm
from matplotlib.pyplot import *
import numpy as np

algos = ["ShiftAnd","AhoCorasick"]
labels = ["Shift-And","Aho-Corasick"]
colors = ['r','g']

total = []
reps = 10

min_patterns = 1
max_patterns = 150
pattern_counts = np.arange(min_patterns,max_patterns+2,10)

for (i, algo) in enumerate(algos):
    times = []
    for pattern_count in pattern_counts:
        print(algo+" "+str(pattern_count))
        tmp = 0; 
        for _ in range(reps):  
            pre, search = time_algorithm(algo, 'Random256', 1000000, pattern_count, 10)
            tmp += pre + search
        times.append(tmp / reps)
    plot(pattern_counts*10, times, color=colors[i],label=labels[i])
    ylabel('total runtime (ms)')
    xlabel(r'$||P\,||$')
xlim(min_patterns*10, max_patterns*10)
ylim(0)
title(r'Shift-And scaling with $||P\,||$')
legend(loc='upper left')
savefig('img\\SAvAC.svg')
