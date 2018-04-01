from mpi4py import MPI
import random

comm = MPI.COMM_WORLD
rank = comm.Get_rank()
numproc = comm.Get_size()
partial_sum = 0

if rank == 0:

    rand_numbers = []

    for i in range(0, numproc):
        rand_numbers.append(random.randint(1, 5))

    print("my numbers are = ", rand_numbers)
    partial_sum = rand_numbers[0]
    print("process ", rank, " got number ", partial_sum)

    for i in range(1, numproc):
        req = comm.isend(rand_numbers, dest=i, tag=0)
        req.wait()

    for i in range(1, numproc):
        req2 = comm.irecv(source=i, tag=i)
        partial_sum += req2.wait()

    print("sum = ", partial_sum)

else:
    req = comm.irecv(source=0, tag=0)
    rand_numbers = req.wait()
    partial_sum = rand_numbers[rank]
    print("process ", rank, " got number ", partial_sum)

    req2 = comm.isend(partial_sum, dest=0, tag=rank)
    req2.wait()