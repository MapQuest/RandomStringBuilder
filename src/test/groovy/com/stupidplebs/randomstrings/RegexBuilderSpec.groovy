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
        regex == "${value}[ ]?[${('a'..'z').join()}]+[0123456789]{$m,$n}($value)?"
        
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
        "oneLetter"              | (('a'..'z') + ('A'..'Z')).join()
        "oneAlphaNumeric"        | (('a'..'z') + ('A'..'Z') + ('0'..'9')).join()
        "oneLowercaseLetter"     | ('a'..'z').join()
        "oneUppercaseLetter"     | ('A'..'Z').join()
        "oneNumber"              | ('0'..'9').join()
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
        "zeroOrMoreLetters"              | (('a'..'z') + ('A'..'Z')).join()
        "zeroOrMoreAlphaNumerics"        | (('a'..'z') + ('A'..'Z') + ('0'..'9')).join()
        "zeroOrMoreLowercaseLetters"     | ('a'..'z').join()
        "zeroOrMoreUppercaseLetters"     | ('A'..'Z').join()
        "zeroOrMoreNumbers"              | ('0'..'9').join()
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
        "atLeastOneLetter"              | (('a'..'z') + ('A'..'Z')).join()
        "atLeastOneAlphaNumeric"        | (('a'..'z') + ('A'..'Z') + ('0'..'9')).join()
        "atLeastOneLowercaseLetter"     | ('a'..'z').join()
        "atLeastOneUppercaseLetter"     | ('A'..'Z').join()
        "atLeastOneNumber"              | ('0'..'9').join()
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
        "atLeastNLetters"              | (('a'..'z') + ('A'..'Z')).join()
        "atLeastNAlphaNumerics"        | (('a'..'z') + ('A'..'Z') + ('0'..'9')).join()
        "atLeastNLowercaseLetters"     | ('a'..'z').join()
        "atLeastNUppercaseLetters"     | ('A'..'Z').join()
        "atLeastNNumbers"              | ('0'..'9').join()
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
        "betweenMAndNLetters"              | (('a'..'z') + ('A'..'Z')).join()
        "betweenMAndNAlphaNumerics"        | (('a'..'z') + ('A'..'Z') + ('0'..'9')).join()
        "betweenMAndNLowercaseLetters"     | ('a'..'z').join()
        "betweenMAndNUppercaseLetters"     | ('A'..'Z').join()
        "betweenMAndNNumbers"              | ('0'..'9').join()
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
        "nLetters"              | (('a'..'z') + ('A'..'Z')).join()
        "nAlphaNumerics"        | (('a'..'z') + ('A'..'Z') + ('0'..'9')).join()
        "nLowercaseLetters"     | ('a'..'z').join()
        "nUppercaseLetters"     | ('A'..'Z').join()
        "nNumbers"              | ('0'..'9').join()
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
        "optionalLetters"              | (('a'..'z') + ('A'..'Z')).join()
        "optionalAlphaNumerics"        | (('a'..'z') + ('A'..'Z') + ('0'..'9')).join()
        "optionalLowercaseLetters"     | ('a'..'z').join()
        "optionalUppercaseLetters"     | ('A'..'Z').join()
        "optionalNumbers"              | ('0'..'9').join()
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
        "optionalLetter"              | (('a'..'z') + ('A'..'Z')).join()
        "optionalAlphaNumeric"        | (('a'..'z') + ('A'..'Z') + ('0'..'9')).join()
        "optionalLowercaseLetter"     | ('a'..'z').join()
        "optionalUppercaseLetter"     | ('A'..'Z').join()
        "optionalNumber"              | ('0'..'9').join()
        "optionalWhitespaceCharacter" | " \t"
        "optionalSpace"               | " "
        
    }
    
}
