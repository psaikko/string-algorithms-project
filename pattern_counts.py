from matplotlib.pyplot import *
import numpy as np
from experiment_util import time_algorithm

algorithms = ['KarpRabin','AhoCorasick','ShiftAnd']
names = ['Karp-Rabin','Aho-Corasick','Shift-And']
pattern_counts = (np.arange(50)+1)*10

reps = 10
for i in [0,1,2]:
    algo = algorithms[i];
    preprocess_times = []
    search_times = []
    total_times = []
    for pattern_count in pattern_counts:
      print(algo + ' ' + str(pattern_count))
      tmp1 = 0;
      tmp2 = 0;
      for _ in range(reps):  
        pre, search = time_algorithm(algo, 'data\\english.200MB', 100000, pattern_count, 10)
        tmp1 += pre
        tmp2 += search
      preprocess_times.append(tmp1 / reps)
      search_times.append(tmp2 / reps)
      total_times.append((tmp1 + tmp2) / reps)
    figure()
    plot(pattern_counts, preprocess_times, label='preprocessing')
    plot(pattern_counts, search_times, label='searching')
    plot(pattern_counts, total_times, label='total')
    legend(loc='upper left')
    xlim(0,500)
    xlabel(r'$|P\,|$')
    ylabel('runtime (ms)')
    savefig('img\\patterncount_'+names[i]+'.pdf')
