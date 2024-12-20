package day17

import getLines

fun main() {
    val solver = Dec17()
    solver.partOne()
}

class Dec17 {
    fun partOne() {
        val lines = getLines("day17")

        val registerA = Regex("Register A: ([0-9]+)").matchEntire(lines[0])!!.groups[1]!!.value.toInt()
        val registerB = Regex("Register B: ([0-9]+)").matchEntire(lines[1])!!.groups[1]!!.value.toInt()
        val registerC = Regex("Register C: ([0-9]+)").matchEntire(lines[2])!!.groups[1]!!.value.toInt()
        val program = Regex("Program: (([0-9],)+[0-9])").matchEntire(lines[4])!!.groups[1]!!.value

        println(
            Computer(
                registerA = registerA,
                registerB = registerB,
                registerC = registerC
            ).runProgram(program = program)
        )
    }

    class Computer(var registerA: Int, var registerB: Int, var registerC: Int) {
        fun runProgram(program: String): String {
            val output = mutableListOf<Int>()
            val instructions = program.split(",").map { it.toInt() }
            var pc = 0
            while (pc < instructions.size) {
                val opcode = instructions[pc]
                val operand = instructions[pc + 1]
                val result = resolveInstruction(opcode, operand)
                if (result.pc != -1) {
                    pc = result.pc
                } else {
                    pc += 2
                }
                if (result.output != -1) {
                    output.add(result.output)
                }
            }
            return output.joinToString(",")
        }

        private fun resolveInstruction(opcode: Int, operand: Int): OperationResult {
            when (opcode) {
                0 -> registerA = (registerA / Math.pow(2.0, combo(operand).toDouble())).toInt() // adv
                1 -> registerB = registerB xor operand // bxl
                2 -> registerB = combo(operand) % 8 // bst
                3 -> if (registerA != 0) return OperationResult(pc = operand, output = -1) // jnz
                4 -> registerB = registerB xor registerC // bxc
                5 -> return OperationResult(pc = -1, output = combo(operand) % 8) // out
                6 -> registerB = (registerA / Math.pow(2.0, combo(operand).toDouble())).toInt() // bdv
                7 -> registerC = (registerA / Math.pow(2.0, combo(operand).toDouble())).toInt() // cdv
            }
            return OperationResult(pc = -1, output = -1)
        }

        private fun combo(operand: Int): Int {
            return when (operand) {
                0, 1, 2, 3 -> operand
                4 -> registerA
                5 -> registerB
                6 -> registerC
                else -> throw IllegalStateException("oh no")
            }
        }
    }

    data class OperationResult(val pc: Int, val output: Int)
}


/**
 * The eight instructions are as follows:
 *
 * The adv instruction (opcode 0) performs division. The numerator is the value in the A register. The denominator is found by raising 2 to the power of the instruction's combo operand. (So, an operand of 2 would divide A by 4 (2^2); an operand of 5 would divide A by 2^B.) The result of the division operation is truncated to an integer and then written to the A register.
 *
 * The bxl instruction (opcode 1) calculates the bitwise XOR of register B and the instruction's literal operand, then stores the result in register B.
 *
 * The bst instruction (opcode 2) calculates the value of its combo operand modulo 8 (thereby keeping only its lowest 3 bits), then writes that value to the B register.
 *
 * The jnz instruction (opcode 3) does nothing if the A register is 0. However, if the A register is not zero, it jumps by setting the instruction pointer to the value of its literal operand; if this instruction jumps, the instruction pointer is not increased by 2 after this instruction.
 *
 * The bxc instruction (opcode 4) calculates the bitwise XOR of register B and register C, then stores the result in register B. (For legacy reasons, this instruction reads an operand but ignores it.)
 *
 * The out instruction (opcode 5) calculates the value of its combo operand modulo 8, then outputs that value. (If a program outputs multiple values, they are separated by commas.)
 *
 * The bdv instruction (opcode 6) works exactly like the adv instruction except that the result is stored in the B register. (The numerator is still read from the A register.)
 *
 * The cdv instruction (opcode 7) works exactly like the adv instruction except that the result is stored in the C register. (The numerator is still read from the A register.)
 *
 * The value of a combo operand can be found as follows:
 *
 * Combo operands 0 through 3 represent literal values 0 through 3.
 * Combo operand 4 represents the value of register A.
 * Combo operand 5 represents the value of register B.
 * Combo operand 6 represents the value of register C.
 * Combo operand 7 is reserved and will not appear in valid programs.
 */