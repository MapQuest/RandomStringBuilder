package com.stupidplebs.randomstrings

import java.util.List;
import java.util.Map;

class RegexBuilder {
    static final def LETTERS = (('a'..'z') + ('A'..'Z')).join()
    static final def NUMBERS = ('0'..'9').join()
    static final def LOWERCASE_LETTERS = ('a'..'z').join()
    static final def UPPERCASE_LETTERS = ('A'..'Z').join()
    static final def ALPHANUMERICS = (('a'..'z') + ('A'..'Z') + ('0'..'9')).join()
    static final def WHITESPACE = " \t"
    
    def final StringBuilder sb = new StringBuilder()

    public RegexBuilder() {}
    
    /**
     * Append the supplied value
     *
     * @param value
     * @return
     */
    public RandomStringBuilder is(value) {
        sb.append(value.toString())
        this
    }
    
    public RandomStringBuilder optional(value) {
        sb.append("($value)?")
        this
    }

    public RandomStringBuilder oneCharacterOf(String chars) {
        sb.append('[').append(chars).append(']')
        this
    }
    
    public RandomStringBuilder oneLetter() {
        oneCharacterOf(LETTERS)
    }
    
    public RandomStringBuilder oneAlphaNumeric() {
        oneCharacterOf(ALPHANUMERICS)
    }
    
    public RandomStringBuilder oneLowercaseLetter() {
        oneCharacterOf(LOWERCASE_LETTERS)
    }
    
    public RandomStringBuilder oneUppercaseLetter() {
        oneCharacterOf(UPPERCASE_LETTERS)
    }
    
    public RandomStringBuilder oneNumber() {
        oneCharacterOf(NUMBERS)
    }
    
    public RandomStringBuilder oneWhitespaceCharacter() {
        oneCharacterOf(WHITESPACE)
    }
    
    public RandomStringBuilder zeroOrMoreCharactersOf(String chars) {
        sb.append('[').append(chars).append(']*')
        this
    }
    
    public RandomStringBuilder zeroOrMoreLetters() {
        zeroOrMoreCharactersOf(LETTERS)
    }
    
    public RandomStringBuilder zeroOrMoreAlphaNumerics() {
        zeroOrMoreCharactersOf(ALPHANUMERICS)
    }
    
    public RandomStringBuilder zeroOrMoreLowercaseLetters() {
        zeroOrMoreCharactersOf(LOWERCASE_LETTERS)
    }
    
    public RandomStringBuilder zeroOrMoreUppercaseLetters() {
        zeroOrMoreCharactersOf(UPPERCASE_LETTERS)
    }
    
    public RandomStringBuilder zeroOrMoreNumbers() {
        zeroOrMoreCharactersOf(NUMBERS)
    }
    
    public RandomStringBuilder zeroOrMoreWhitespaceCharacters() {
        zeroOrMoreCharactersOf(WHITESPACE)
    }
    
    public RandomStringBuilder atLeastOneCharacterOf(String chars) {
        sb.append('[').append(chars).append(']+')
        this
    }
    
    public RandomStringBuilder atLeastOneLetter() {
        atLeastOneCharacterOf(LETTERS)
    }
    
    public RandomStringBuilder atLeastOneAlphaNumeric() {
        atLeastOneCharacterOf(ALPHANUMERICS)
    }
    
    public RandomStringBuilder atLeastOneLowercaseLetter() {
        atLeastOneCharacterOf(LOWERCASE_LETTERS)
    }
    
    public RandomStringBuilder atLeastOneUppercaseLetter() {
        atLeastOneCharacterOf(UPPERCASE_LETTERS)
    }
    
    public RandomStringBuilder atLeastOneNumber() {
        atLeastOneCharacterOf(NUMBERS)
    }
    
    public RandomStringBuilder atLeastOneWhitespaceCharacter() {
        atLeastOneCharacterOf(WHITESPACE)
    }
    
    public RandomStringBuilder atLeastNCharactersOf(String chars, Integer n) {
        sb.append('[').append(chars).append(']{').append(n).append(',}')
        this
    }
    
    public RandomStringBuilder atLeastNLetters(Integer n) {
        atLeastNCharactersOf(LETTERS, n)
    }
    
    public RandomStringBuilder atLeastNAlphaNumerics(Integer n) {
        atLeastNCharactersOf(ALPHANUMERICS, n)
    }
    
    public RandomStringBuilder atLeastNLowercaseLetters(Integer n) {
        atLeastNCharactersOf(LOWERCASE_LETTERS, n)
    }
    
    public RandomStringBuilder atLeastNUppercaseLetters(Integer n) {
        atLeastNCharactersOf(UPPERCASE_LETTERS, n)
    }
    
    public RandomStringBuilder atLeastNNumbers(Integer n) {
        atLeastNCharactersOf(NUMBERS, n)
    }
    
    public RandomStringBuilder atLeastNWhitespaceCharacters(Integer n) {
        atLeastNCharactersOf(WHITESPACE, n)
    }
    
    public RandomStringBuilder betweenMAndNCharactersOf(String chars, Integer m, Integer n) {
        sb.append('[').append(chars).append(']{').append(m).append(',').append(n).append('}')
        this
    }
    
    public RandomStringBuilder betweenMAndNLetters(Integer m, Integer n) {
        betweenMAndNCharactersOf(LETTERS, m, n)
    }
    
    public RandomStringBuilder betweenMAndNAlphaNumerics(Integer m, Integer n) {
        betweenMAndNCharactersOf(ALPHANUMERICS, m, n)
    }
    
    public RandomStringBuilder betweenMAndNLowercaseLetters(Integer m, Integer n) {
        betweenMAndNCharactersOf(LOWERCASE_LETTERS, m, n)
    }
    
    public RandomStringBuilder betweenMAndNUppercaseLetters(Integer m, Integer n) {
        betweenMAndNCharactersOf(UPPERCASE_LETTERS, m, n)
    }
    
    public RandomStringBuilder betweenMAndNNumbers(Integer m, Integer n) {
        betweenMAndNCharactersOf(NUMBERS, m, n)
    }
    
    public RandomStringBuilder betweenMAndNWhitespaceCharacters(Integer m, Integer n) {
        betweenMAndNCharactersOf(WHITESPACE, m, n)
    }
    
    public RandomStringBuilder optionalCharactersOf(String chars) {
        sb.append('([').append(chars).append(']+)?')
        this
    }
    
    public RandomStringBuilder optionalLetters() {
        optionalCharactersOf(LETTERS)
    }
    
    public RandomStringBuilder optionalAlphaNumerics() {
        optionalCharactersOf(ALPHANUMERICS)
    }
    
    public RandomStringBuilder optionalLowercaseLetters() {
        optionalCharactersOf(LOWERCASE_LETTERS)
    }
    
    public RandomStringBuilder optionalUppercaseLetters() {
        optionalCharactersOf(UPPERCASE_LETTERS)
    }
    
    public RandomStringBuilder optionalNumbers() {
        optionalCharactersOf(NUMBERS)
    }
    
    public RandomStringBuilder optionalWhitespaceCharacters() {
        optionalCharactersOf(WHITESPACE)
    }
    
    public RandomStringBuilder optionalCharacterOf(String chars) {
        sb.append('[').append(chars).append(']?')
        this
    }
    
    public RandomStringBuilder optionalLetter() {
        optionalCharacterOf(LETTERS)
    }
    
    public RandomStringBuilder optionalAlphaNumeric() {
        optionalCharacterOf(ALPHANUMERICS)
    }
    
    public RandomStringBuilder optionalLowercaseLetter() {
        optionalCharacterOf(LOWERCASE_LETTERS)
    }
    
    public RandomStringBuilder optionalUppercaseLetter() {
        optionalCharacterOf(UPPERCASE_LETTERS)
    }
    
    public RandomStringBuilder optionalNumber() {
        optionalCharacterOf(NUMBERS)
    }
    
    public RandomStringBuilder optionalWhitespaceCharacter() {
        optionalCharacterOf(WHITESPACE)
    }
    
    public RandomStringBuilder nCharactersOf(String chars, Integer n) {
        sb.append('[').append(chars).append(']{').append(n).append('}')
        this
    }
    
    public RandomStringBuilder nLetters(Integer n) {
        nCharactersOf(LETTERS, n)
    }
    
    public RandomStringBuilder nAlphaNumerics(Integer n) {
        nCharactersOf(ALPHANUMERICS, n)
    }
    
    public RandomStringBuilder nLowercaseLetters(Integer n) {
        nCharactersOf(LOWERCASE_LETTERS, n)
    }
    
    public RandomStringBuilder nUppercaseLetters(Integer n) {
        nCharactersOf(UPPERCASE_LETTERS, n)
    }
    
    public RandomStringBuilder nNumbers(Integer n) {
        nCharactersOf(NUMBERS, n)
    }
    
    public RandomStringBuilder nWhitespaceCharacters(Integer n) {
        nCharactersOf(WHITESPACE, n)
    }
    
    public String build() {
        sb.toString()
    }

}
