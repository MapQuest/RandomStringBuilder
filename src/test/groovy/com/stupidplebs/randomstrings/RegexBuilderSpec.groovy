package com.stupidplebs.randomstrings

import spock.lang.Shared
import spock.lang.Specification

class RegexBuilderSpec extends Specification {
    @Shared def random = new Random()
    
    def "methods should chain patterns together"() {
        given:
        def m = Math.abs(random.nextInt())
        def n = Math.abs(random.nextInt())

        and:        
        def value = new RandomStringBuilder().atLeastOneAlphaNumeric().build()

        and:
        def regex = new RegexBuilder().
                is(value).
                optionalSpace().
                atLeastOneLowercaseLetter().
                betweenMAndNNumbers(m, n).
                optional(value).
                build()
                
        expect:
        regex == "${value}[ ]?[a-z]+[0-9]{$m,$n}($value)?"
        
    }
    
    def "is with add explicit value"() {
        given:
        def value = new RandomStringBuilder().atLeastOneAlphaNumeric().build()
        
        and:
        def regex = new RegexBuilder().is(value).build()
        
        expect:
        regex == value
        
    }
    
    def "optional should return value with optional syntax"() {
        given:
        def value = new RandomStringBuilder().atLeastOneAlphaNumeric().build()
        
        and:
        def regex = new RegexBuilder().optional(value).build()
        
        expect:
        regex == "(${value})?"

    }
    
    def "oneCharacterOf should return pattern wrapped in square brackets"() {
        given:
        def value = new RandomStringBuilder().atLeastOneAlphaNumeric().build()
        
        and:
        def regex = new RegexBuilder().oneCharacterOf(value).build()
        
        expect:
        regex == "[${value}]"

    }
    
    def "one* methods should return value wrapped in square brackets"() {
        given:
        def regex = new RegexBuilder()."$method"().build()
        
        expect:
        regex == "[${pattern}]"

        where:
        method                   | pattern
        "oneLetter"              | "a-zA-Z"
        "oneAlphaNumeric"        | "a-zA-Z0-9"
        "oneLowercaseLetter"     | "a-z"
        "oneUppercaseLetter"     | "A-Z"
        "oneNumber"              | "0-9"
        "oneWhitespaceCharacter" | " \t"
        "oneSpace"               | " "
    }
    
    def "zeroOrMoreCharactersOf should return pattern wrapped in square brackets with '*' quantifer"() {
        given:
        def value = new RandomStringBuilder().atLeastOneAlphaNumeric().build()
        
        and:
        def regex = new RegexBuilder().zeroOrMoreCharactersOf(value).build()
        
        expect:
        regex == "[${value}]*"

    }
    
    def "zeroOrMore* methods should return value wrapped in square brackets with '*' quantifer"() {
        given:
        def regex = new RegexBuilder()."$method"().build()
        
        expect:
        regex == "[${pattern}]*"

        where:
        method                           | pattern
        "zeroOrMoreLetters"              | "a-zA-Z"
        "zeroOrMoreAlphaNumerics"        | "a-zA-Z0-9"
        "zeroOrMoreLowercaseLetters"     | "a-z"
        "zeroOrMoreUppercaseLetters"     | "A-Z"
        "zeroOrMoreNumbers"              | "0-9"
        "zeroOrMoreWhitespaceCharacters" | " \t"
        "zeroOrMoreSpaces"               | " "
        
    }
    
    def "atLeastOneCharacterOf should return pattern wrapped in square brackets with '+' quantifer"() {
        given:
        def value = new RandomStringBuilder().atLeastOneAlphaNumeric().build()
        
        and:
        def regex = new RegexBuilder().atLeastOneCharacterOf(value).build()
        
        expect:
        regex == "[${value}]+"

    }
    
    def "atLeastOne* methods should return value wrapped in square brackets with '+' quantifer"() {
        given:
        def regex = new RegexBuilder()."$method"().build()
        
        expect:
        regex == "[${pattern}]+"

        where:
        method                          | pattern
        "atLeastOneLetter"              | "a-zA-Z"
        "atLeastOneAlphaNumeric"        | "a-zA-Z0-9"
        "atLeastOneLowercaseLetter"     | "a-z"
        "atLeastOneUppercaseLetter"     | "A-Z"
        "atLeastOneNumber"              | "0-9"
        "atLeastOneWhitespaceCharacter" | " \t"
        "atLeastOneSpace"               | " "
        
    }
    
    def "atLeastNCharactersOf should return pattern wrapped in square brackets with 'n,' quantifer"() {
        given:
        def n = Math.abs(random.nextInt())

        and:        
        def value = new RandomStringBuilder().atLeastOneAlphaNumeric().build()
        
        and:
        def regex = new RegexBuilder().atLeastNCharactersOf(value, n).build()
        
        expect:
        regex == "[${value}]{${n},}"

    }
    
    def "atLeastN* methods should return value wrapped in square brackets with 'n,' quantifer"() {
        given:
        def n = Math.abs(random.nextInt())

        and:        
        def regex = new RegexBuilder()."$method"(n).build()
        
        expect:
        regex == "[${pattern}]{${n},}"

        where:
        method                         | pattern
        "atLeastNLetters"              | "a-zA-Z"
        "atLeastNAlphaNumerics"        | "a-zA-Z0-9"
        "atLeastNLowercaseLetters"     | "a-z"
        "atLeastNUppercaseLetters"     | "A-Z"
        "atLeastNNumbers"              | "0-9"
        "atLeastNWhitespaceCharacters" | " \t"
        "atLeastNSpaces"               | " "
        
    }
    
    def "betweenMAndNCharactersOf should return pattern wrapped in square brackets with 'n,' quantifer"() {
        given:
        def m = Math.abs(random.nextInt())
        def n = Math.abs(random.nextInt())

        and:        
        def value = new RandomStringBuilder().atLeastOneAlphaNumeric().build()
        
        and:
        def regex = new RegexBuilder().betweenMAndNCharactersOf(value, m, n).build()
        
        expect:
        regex == "[${value}]{${m},${n}}"

    }
    
    def "betweenMAndN* methods should return value wrapped in square brackets with 'n,' quantifer"() {
        given:
        def m = Math.abs(random.nextInt())
        def n = Math.abs(random.nextInt())

        and:        
        def regex = new RegexBuilder()."$method"(m, n).build()
        
        expect:
        regex == "[${pattern}]{${m},${n}}"

        where:
        method                             | pattern
        "betweenMAndNLetters"              | "a-zA-Z"
        "betweenMAndNAlphaNumerics"        | "a-zA-Z0-9"
        "betweenMAndNLowercaseLetters"     | "a-z"
        "betweenMAndNUppercaseLetters"     | "A-Z"
        "betweenMAndNNumbers"              | "0-9"
        "betweenMAndNWhitespaceCharacters" | " \t"
        "betweenMAndNSpaces"               | " "
        
    }
    
    def "nCharactersOf should return pattern wrapped in square brackets with 'n,' quantifer"() {
        given:
        def n = Math.abs(random.nextInt())

        and:        
        def value = new RandomStringBuilder().atLeastOneAlphaNumeric().build()
        
        and:
        def regex = new RegexBuilder().nCharactersOf(value, n).build()
        
        expect:
        regex == "[${value}]{${n}}"

    }
    
    def "n* methods should return value wrapped in square brackets with 'n,' quantifer"() {
        given:
        def n = Math.abs(random.nextInt())

        and:        
        def regex = new RegexBuilder()."$method"(n).build()
        
        expect:
        regex == "[${pattern}]{${n}}"

        where:
        method                  | pattern
        "nLetters"              | "a-zA-Z"
        "nAlphaNumerics"        | "a-zA-Z0-9"
        "nLowercaseLetters"     | "a-z"
        "nUppercaseLetters"     | "A-Z"
        "nNumbers"              | "0-9"
        "nWhitespaceCharacters" | " \t"
        "nSpaces"               | " "
        
    }
    
    def "optionalCharactersOf should return pattern wrapped in square brackets with 'n,' quantifer"() {
        given:
        def value = new RandomStringBuilder().atLeastOneAlphaNumeric().build()
        
        and:
        def regex = new RegexBuilder().optionalCharactersOf(value).build()
        
        expect:
        regex == "([${value}]+)?"

    }
    
    def "many optional* methods should return value wrapped in square brackets with 'n,' quantifer"() {
        given:
        def regex = new RegexBuilder()."$method"().build()
        
        expect:
        regex == "([${pattern}]+)?"

        where:
        method                         | pattern
        "optionalLetters"              | "a-zA-Z"
        "optionalAlphaNumerics"        | "a-zA-Z0-9"
        "optionalLowercaseLetters"     | "a-z"
        "optionalUppercaseLetters"     | "A-Z"
        "optionalNumbers"              | "0-9"
        "optionalWhitespaceCharacters" | " \t"
        "optionalSpaces"               | " "
        
    }
    
    def "optionalCharacterOf should return pattern wrapped in square brackets with 'n,' quantifer"() {
        given:
        def value = new RandomStringBuilder().atLeastOneAlphaNumeric().build()
        
        and:
        def regex = new RegexBuilder().optionalCharacterOf(value).build()
        
        expect:
        regex == "[${value}]?"

    }
    
    def "single optional* methods should return value wrapped in square brackets with 'n,' quantifer"() {
        given:
        def regex = new RegexBuilder()."$method"().build()
        
        expect:
        regex == "[${pattern}]?"

        where:
        method                        | pattern
        "optionalLetter"              | "a-zA-Z"
        "optionalAlphaNumeric"        | "a-zA-Z0-9"
        "optionalLowercaseLetter"     | "a-z"
        "optionalUppercaseLetter"     | "A-Z"
        "optionalNumber"              | "0-9"
        "optionalWhitespaceCharacter" | " \t"
        "optionalSpace"               | " "
        
    }
    
}
