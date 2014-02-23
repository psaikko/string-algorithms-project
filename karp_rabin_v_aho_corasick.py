from experiment_util import time_algorithm
from matplotlib.pyplot import *
import numpy as np

algos = ["KarpRabin","AhoCorasick"]
labels = ["Karp-Rabin","Aho-Corasick"]
colors = ['r','g']

total = []
reps = 10

min_patterns = 1
max_patterns = 500
pattern_counts = np.arange(min_patterns,max_patterns+2,10)

for (i, algo) in enumerate(algos):
    times = []
    for pattern_count in pattern_counts:
        print(algo+" "+str(pattern_count))
        tmp = 0; 
        for _ in range(reps):  
            pre, search = time_algorithm(algo, 'Random40', 100000, pattern_count, 5)
            tmp += search
        times.append(tmp / reps)
    plot(pattern_counts, times, color=colors[i], label=labels[i])
    ylabel('time (ms)')
    xlabel(r'$|\mathcal{P}\,|$')
xlim(min_patterns, max_patterns)
legend(loc='upper left')
savefig('img\\KRvAC.pdf')
