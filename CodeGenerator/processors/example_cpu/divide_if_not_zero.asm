	LC R3 #0
	LC R4 #0
l1:	SP2 R3
l2:	LP0 R0
	LP1 R1
	BEQ R1 R3 l1
	DIVU R0 R1 R2
	SP2 R2
	JMP l2
