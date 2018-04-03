from mpi4py import MPI
import random
import math

comm = MPI.COMM_WORLD
rank = comm.Get_rank()
numproc = comm.Get_size()
partial_sum = 0
rand_numbers = []

for i in range(0, numproc*random.randint(1,5)):
    rand_numbers.append(random.randint(1, 5))

chunksize = int(math.ceil(len(rand_numbers)/numproc))

if rank == 0:

    print("my numbers are = ", rand_numbers)

    for element in range(rank*chunksize, rank*chunksize+chunksize):
        partial_sum += rand_numbers[element]

    for i in range(1, numproc):
        req = comm.isend(rand_numbers[i*chunksize:i*chunksize+chunksize:1], dest=i, tag=0)
        req.wait()

    for i in range(1, numproc):
        req2 = comm.irecv(source=i, tag=i)
        partial_sum += req2.wait()

    print("sum = ", partial_sum)

else:

    req = comm.irecv(source=0, tag=0)
    rand_numbers = req.wait()

    for number in rand_numbers:
        partial_sum += number

    req2 = comm.isend(partial_sum, dest=0, tag=rank)
    req2.wait()