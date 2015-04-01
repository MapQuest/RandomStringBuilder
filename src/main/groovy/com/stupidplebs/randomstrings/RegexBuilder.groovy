package com.stupidplebs.randomstrings


class RegexBuilder {
    static final def LETTERS = "a-zA-Z"
    static final def NUMBERS = "0-9"
    static final def LOWERCASE_LETTERS = "a-z"
    static final def UPPERCASE_LETTERS = "A-Z"
    static final def ALPHANUMERICS = "a-zA-Z0-9"
    static final def WHITESPACE = " \t"
    
    def final StringBuilder sb = new StringBuilder()

    public RegexBuilder() {}
    
    /**
     * Append the supplied value
     *
     * @param value
     * @return
     */
    public RegexBuilder is(value) {
        sb.append(value.toString())
        this
    }
    
    public RegexBuilder optional(value) {
        sb.append("($value)?")
        this
    }

    public RegexBuilder oneCharacterOf(String chars) {
        sb.append('[').append(chars).append(']')
        this
    }
    
    public RegexBuilder oneLetter() {
        oneCharacterOf(LETTERS)
    }
    
    public RegexBuilder oneAlphaNumeric() {
        oneCharacterOf(ALPHANUMERICS)
    }
    
    public RegexBuilder oneLowercaseLetter() {
        oneCharacterOf(LOWERCASE_LETTERS)
    }
    
    public RegexBuilder oneUppercaseLetter() {
        oneCharacterOf(UPPERCASE_LETTERS)
    }
    
    public RegexBuilder oneNumber() {
        oneCharacterOf(NUMBERS)
    }
    
    public RegexBuilder oneWhitespaceCharacter() {
        oneCharacterOf(WHITESPACE)
    }
    
    public RegexBuilder oneSpace() {
        oneCharacterOf(' ')
    }
    
    public RegexBuilder zeroOrMoreCharactersOf(String chars) {
        sb.append('[').append(chars).append(']*')
        this
    }
    
    public RegexBuilder zeroOrMoreLetters() {
        zeroOrMoreCharactersOf(LETTERS)
    }
    
    public RegexBuilder zeroOrMoreAlphaNumerics() {
        zeroOrMoreCharactersOf(ALPHANUMERICS)
    }
    
    public RegexBuilder zeroOrMoreLowercaseLetters() {
        zeroOrMoreCharactersOf(LOWERCASE_LETTERS)
    }
    
    public RegexBuilder zeroOrMoreUppercaseLetters() {
        zeroOrMoreCharactersOf(UPPERCASE_LETTERS)
    }
    
    public RegexBuilder zeroOrMoreNumbers() {
        zeroOrMoreCharactersOf(NUMBERS)
    }
    
    public RegexBuilder zeroOrMoreWhitespaceCharacters() {
        zeroOrMoreCharactersOf(WHITESPACE)
    }
    
    public RegexBuilder zeroOrMoreSpaces() {
        zeroOrMoreCharactersOf(' ')
    }
    
    public RegexBuilder atLeastOneCharacterOf(String chars) {
        sb.append('[').append(chars).append(']+')
        this
    }
    
    public RegexBuilder atLeastOneLetter() {
        atLeastOneCharacterOf(LETTERS)
    }
    
    public RegexBuilder atLeastOneAlphaNumeric() {
        atLeastOneCharacterOf(ALPHANUMERICS)
    }
    
    public RegexBuilder atLeastOneLowercaseLetter() {
        atLeastOneCharacterOf(LOWERCASE_LETTERS)
    }
    
    public RegexBuilder atLeastOneUppercaseLetter() {
        atLeastOneCharacterOf(UPPERCASE_LETTERS)
    }
    
    public RegexBuilder atLeastOneNumber() {
        atLeastOneCharacterOf(NUMBERS)
    }
    
    public RegexBuilder atLeastOneWhitespaceCharacter() {
        atLeastOneCharacterOf(WHITESPACE)
    }
    
    public RegexBuilder atLeastOneSpace() {
        atLeastOneCharacterOf(' ')
    }
    
    public RegexBuilder atLeastNCharactersOf(String chars, Integer n) {
        sb.append('[').append(chars).append(']{').append(n).append(',}')
        this
    }
    
    public RegexBuilder atLeastNLetters(Integer n) {
        atLeastNCharactersOf(LETTERS, n)
    }
    
    public RegexBuilder atLeastNAlphaNumerics(Integer n) {
        atLeastNCharactersOf(ALPHANUMERICS, n)
    }
    
    public RegexBuilder atLeastNLowercaseLetters(Integer n) {
        atLeastNCharactersOf(LOWERCASE_LETTERS, n)
    }
    
    public RegexBuilder atLeastNUppercaseLetters(Integer n) {
        atLeastNCharactersOf(UPPERCASE_LETTERS, n)
    }
    
    public RegexBuilder atLeastNNumbers(Integer n) {
        atLeastNCharactersOf(NUMBERS, n)
    }
    
    public RegexBuilder atLeastNWhitespaceCharacters(Integer n) {
        atLeastNCharactersOf(WHITESPACE, n)
    }
    
    public RegexBuilder atLeastNSpaces(Integer n) {
        atLeastNCharactersOf(' ', n)
    }
    
    public RegexBuilder betweenMAndNCharactersOf(String chars, Integer m, Integer n) {
        sb.append('[').append(chars).append(']{').append(m).append(',').append(n).append('}')
        this
    }
    
    public RegexBuilder betweenMAndNLetters(Integer m, Integer n) {
        betweenMAndNCharactersOf(LETTERS, m, n)
    }
    
    public RegexBuilder betweenMAndNAlphaNumerics(Integer m, Integer n) {
        betweenMAndNCharactersOf(ALPHANUMERICS, m, n)
    }
    
    public RegexBuilder betweenMAndNLowercaseLetters(Integer m, Integer n) {
        betweenMAndNCharactersOf(LOWERCASE_LETTERS, m, n)
    }
    
    public RegexBuilder betweenMAndNUppercaseLetters(Integer m, Integer n) {
        betweenMAndNCharactersOf(UPPERCASE_LETTERS, m, n)
    }
    
    public RegexBuilder betweenMAndNNumbers(Integer m, Integer n) {
        betweenMAndNCharactersOf(NUMBERS, m, n)
    }
    
    public RegexBuilder betweenMAndNWhitespaceCharacters(Integer m, Integer n) {
        betweenMAndNCharactersOf(WHITESPACE, m, n)
    }
    
    public RegexBuilder betweenMAndNSpaces(Integer m, Integer n) {
        betweenMAndNCharactersOf(' ', m, n)
    }
    
    public RegexBuilder nLetters(Integer n) {
        nCharactersOf(LETTERS, n)
    }
    
    public RegexBuilder nAlphaNumerics(Integer n) {
        nCharactersOf(ALPHANUMERICS, n)
    }
    
    public RegexBuilder nLowercaseLetters(Integer n) {
        nCharactersOf(LOWERCASE_LETTERS, n)
    }
    
    public RegexBuilder nUppercaseLetters(Integer n) {
        nCharactersOf(UPPERCASE_LETTERS, n)
    }
    
    public RegexBuilder nNumbers(Integer n) {
        nCharactersOf(NUMBERS, n)
    }
    
    public RegexBuilder nWhitespaceCharacters(Integer n) {
        nCharactersOf(WHITESPACE, n)
    }
    
    public RegexBuilder nSpaces(Integer n) {
        nCharactersOf(' ', n)
    }
    
    public RegexBuilder optionalCharactersOf(String chars) {
        sb.append('([').append(chars).append(']+)?')
        this
    }
    
    public RegexBuilder optionalLetters() {
        optionalCharactersOf(LETTERS)
    }
    
    public RegexBuilder optionalAlphaNumerics() {
        optionalCharactersOf(ALPHANUMERICS)
    }
    
    public RegexBuilder optionalLowercaseLetters() {
        optionalCharactersOf(LOWERCASE_LETTERS)
    }
    
    public RegexBuilder optionalUppercaseLetters() {
        optionalCharactersOf(UPPERCASE_LETTERS)
    }
    
    public RegexBuilder optionalNumbers() {
        optionalCharactersOf(NUMBERS)
    }
    
    public RegexBuilder optionalWhitespaceCharacters() {
        optionalCharactersOf(WHITESPACE)
    }
    
    public RegexBuilder optionalSpaces() {
        optionalCharactersOf(' ')
    }
    
    public RegexBuilder optionalCharacterOf(String chars) {
        sb.append('[').append(chars).append(']?')
        this
    }
    
    public RegexBuilder optionalLetter() {
        optionalCharacterOf(LETTERS)
    }
    
    public RegexBuilder optionalAlphaNumeric() {
        optionalCharacterOf(ALPHANUMERICS)
    }
    
    public RegexBuilder optionalLowercaseLetter() {
        optionalCharacterOf(LOWERCASE_LETTERS)
    }
    
    public RegexBuilder optionalUppercaseLetter() {
        optionalCharacterOf(UPPERCASE_LETTERS)
    }
    
    public RegexBuilder optionalNumber() {
        optionalCharacterOf(NUMBERS)
    }
    
    public RegexBuilder optionalWhitespaceCharacter() {
        optionalCharacterOf(WHITESPACE)
    }
    
    public RegexBuilder optionalSpace() {
        optionalCharacterOf(' ')
    }
    
    public RegexBuilder nCharactersOf(String chars, Integer n) {
        sb.append('[').append(chars).append(']{').append(n).append('}')
        this
    }
    
    public String build() {
        sb.toString()
    }

}
