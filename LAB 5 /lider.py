from mpi4py import MPI
import random 

comm = MPI.COMM_WORLD
rank = comm.Get_rank()
numproc = comm.Get_size()
numarul_meu = random.randint(10,20)
numerele_primite = {}
lider = rank

for i in range(0,numproc):
    if (i != rank):
        req = comm.isend(numarul_meu, dest=i, tag=0)
        req.wait()

for i in range(0,numproc):
    if (i != rank):
        req = comm.irecv(source=i, tag=0)
        numerele_primite[i] = req.wait()
        
maxim = numarul_meu
for element in numerele_primite:
    if maxim < numerele_primite[element]:
        maxim = numerele_primite[element]

keys = []
if numarul_meu == maxim:
    keys.append(rank)

for k,v in numerele_primite.items():
    if v==maxim:
        keys.append(k)

print("rank",rank,"numarul meu", numarul_meu,"lider",max(keys))