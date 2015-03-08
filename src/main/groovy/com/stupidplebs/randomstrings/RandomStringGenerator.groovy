package com.stupidplebs.randomstrings

class RandomStringGenerator {
    static final def allLetters = (('a'..'z') + ('A'..'Z')).join()
    static final def numbers = ('0'..'9').join()
    static final def lowercaseLetters = ('a'..'z').join()
    static final def uppercaseLetters = ('A'..'Z').join()
    static final def whitespace = " \t"
    
    private RandomStringGenerator() {}

    public static class Builder {
        def final RandomnessProvider randomnessProvider
        def final StringBuilder sb = new StringBuilder()
        def Integer randomUpperLimit = 25

        public Builder() {
            this.randomnessProvider = new JavaUtilRandomnessProvider()
        }
        
        public Builder(final RandomnessProvider randomnessProvider) {
            this.randomnessProvider = randomnessProvider
        }
        
        public randomUpperLimit(Integer randomUpperLimit) {
            this.randomUpperLimit = randomUpperLimit
        }
        
        public Builder value(s) {
            sb.append(s.toString())
            this
        }
        
        // RANDOM CHARACTERS
        public Builder randomCharactersOf(s, count=1) {
            sb.append((0..<count).collect {
                s[ randomnessProvider.nextInt(s.length())]
            }.join())
            this
        }
        
        public Builder randomLetters(count=1) {
            randomCharactersOf(allLetters, count)
        }

        public Builder randomLetters(ObjectRange range) {
            randomCharactersOf(allLetters, randomnessProvider.nextInt())
        }

        public Builder randomNumbers(count=1) {
            randomCharactersOf(numbers, count)
        }
        
        public Builder randomLowercaseLetters(count=1) {
            randomCharactersOf(lowercaseLetters, count)
        }

        public Builder randomUppercaseLetters(count=1) {
            randomCharactersOf(uppercaseLetters, count)
        }

        public Builder randomWhitespaceCharacters(count=1) {
            randomCharactersOf(whitespace, count)
        }
        
        public Builder randomElement(List l) {
            value(l[randomnessProvider.nextInt(l.size())])
        }
        
        public Builder randomKey(Map m) {
            value(m.collect{ it.key }[randomnessProvider.nextInt(m.size())])
        }
        
        public Builder randomValue(Map m) {
            value(m.collect{ it.value }[randomnessProvider.nextInt(m.size())])
        }
        
        // EXACTLY ONE
        public Builder oneCharacterOf(chars) {
            randomCharactersOf(chars, 1)
        }
        
        public Builder oneLetter() {
            randomCharactersOf(allLetters, 1)
        }
        
        public Builder oneNumber() {
            randomCharactersOf(numbers, 1)
        }
        
        public Builder oneLowercaseLetter() {
            randomCharactersOf(lowercaseLetters, 1)
        }
        
        public Builder oneUppercaseLetter() {
            randomCharactersOf(uppercaseLetters, 1)
        }
        
        public Builder oneWhitespaceCharacter() {
            randomCharactersOf(whitespace, 1)
        }
        
        // ZERO OR MORE
        public Builder zeroOrMoreCharactersOf(s, butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(s, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreLetters(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(allLetters, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreLowercaseLetters(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(lowercaseLetters, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreUppercaseLetters(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(uppercaseLetters, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreNumbers(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(numbers, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        public Builder zeroOrMoreWhitespaceCharacters(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(whitespace, randomnessProvider.nextInt(butNoMoreThan))
        }
        
        // AT LEAST ONE
        public Builder atLeastOneCharacterOf(chars, butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(chars, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneLetter(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(allLetters, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneLowercaseLetter(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(lowercaseLetters, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneUppercaseLetter(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(uppercaseLetters, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneNumber(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(numbers, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        public Builder atLeastOneWhitespaceCharacter(butNoMoreThan=randomUpperLimit) {
            randomCharactersOf(whitespace, randomnessProvider.nextInt(butNoMoreThan) ?: 1)
        }
        
        // OPTIONAL
        public Builder optionalCharacterOf(s, count=1) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(s, count) : this
        }
        
        public Builder optionalLetters(count=1) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(allLetters, count) : this
        }
        
        public Builder optionalNumbers(count=1) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(numbers, count) : this
        }
        
        public Builder optionalLowercaseLetters(count=1) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(lowercaseLetters, count) : this
        }
        
        public Builder optionalUppercaseLetters(count=1) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(uppercaseLetters, count) : this
        }
        
        public Builder optionalWhitespaceCharacters(count=1) {
            randomnessProvider.nextBoolean() ? randomCharactersOf(whitespace, count) : this
        }
        
        public Builder optionalElement(List l) {
            randomnessProvider.nextBoolean() ? randomElement(l) : this
        }
        
        public Builder optionalKey(Map m) {
            randomnessProvider.nextBoolean() ? randomKey(m) : this
        }
        
        public Builder optionalValue(Map m) {
            randomnessProvider.nextBoolean() ? randomValue(m) : this
        }
        
        public String build() {
            sb.toString()
        }
        
    }
    
}
