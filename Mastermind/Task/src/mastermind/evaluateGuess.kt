package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val nonFunct = evaluateGuessNonFunctional(secret, guess)
    val funct = evaluateGuessFunctional(secret, guess)
    return Evaluation(funct.rightPosition.coerceAtMost(nonFunct.rightPosition),
            nonFunct.wrongPosition.coerceAtMost(funct.wrongPosition))
}

private fun evaluateGuessNonFunctional(secret: String, guess: String): Evaluation {
    var rightPos = 0;

    // First get the chars at right position
    for (pos in 0..3) {
        if (secret[pos] == guess[pos]) {
            rightPos += 1
        }
    }

    // Get the total common characters
    var commonLetters = 0
    for (c in "ABCDEF".toCharArray()) {
        commonLetters += secret.count { it == c }.coerceAtMost(guess.count { it == c })
    }

    return Evaluation(rightPos, commonLetters - rightPos)
}

private fun evaluateGuessFunctional(secret: String, guess: String): Evaluation {
    val rightPositions = secret.zip(guess).count { pair -> pair.first == pair.second }

    val commonLetters = "ABCDEF".sumBy { ch ->
        secret.count { it == ch }.coerceAtMost(guess.count { it == ch })
    }
    return Evaluation(rightPositions, commonLetters - rightPositions)
}