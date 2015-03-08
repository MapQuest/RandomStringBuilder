package com.stupidplebs.randomstrings

import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges

@ConfineMetaClassChanges(List)
class RandomStringGeneratorSpec extends Specification {
    def setupSpec() {
        List.metaClass.collectWithIndex = { body->
            [delegate, 0..<delegate.size()].transpose().collect(body)
        }
    }

    def "value should append without calling randomnessProvider"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * _(_)
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            value("Value1").
            value("Value2").
            build()
            
        then:
        actualString == "Value1Value2"

    }
    
    def "randomCharactersOf should append 1 character from the string when no count parameter is specified"() {
        given:
        def characters = 'stupid'
        
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(characters.length()) >> charIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomCharactersOf(characters).
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << "stupid".toList().collectWithIndex { it, idx -> [idx, it] }
        
    }
    
    def "randomCharactersOf should append 'count' characters from the string when count is specified"() {
        given:
        def characters = 'stupid'
        
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            count * nextInt(characters.length()) >>> charIndexes
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomCharactersOf(characters, count).
            build()
            
        then:
        actualString == expectedString
        
        where:
        count | charIndexes       | expectedString
        3     | [1, 3, 5]         | "tpd"
        5     | [1, 3, 5, 0, 1]   | "tpdst"
        
    }
    
    def "randomLetters should append 1 character from the a-zA-Z when no count parameter is specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(52) >> charIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomLetters().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << (('a'..'z') + ('A'..'Z')).collectWithIndex { it, idx -> [idx, it] }

    }
    
    def "randomLetters should append 'count' characters from the a-zA-Z when count parameter is specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            count * nextInt(52) >>> charIndexes
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomLetters(count).
            build()
            
        then:
        actualString == expectedOutput

        where:
        count | charIndexes       | expectedOutput
        4     | [0, 25, 26, 51]   | "azAZ"
        52    | (0..51)           | "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    }
    
    def "randomLowercaseLetters should append 1 character from the a-z when no count parameter is specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(26) >> charIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomLowercaseLetters().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << ('a'..'z').collectWithIndex { it, idx -> [idx, it] }

    }
    
    def "randomLowercaseLetters should append 'count' characters from the a-z when count parameter is specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            count * nextInt(26) >>> charIndexes
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomLowercaseLetters(count).
            build()
            
        then:
        actualString == expectedOutput

        where:
        count | charIndexes | expectedOutput
        3     | [0, 13, 25] | "anz"
        26    | (0..25)     | "abcdefghijklmnopqrstuvwxyz"

    }
    
    def "randomUppercaseLetters should append 1 character from the a-z when no count parameter is specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(26) >> charIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomUppercaseLetters().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << ('A'..'Z').collectWithIndex { it, idx -> [idx, it] }

    }
    
    def "randomUppercaseLetters should append 'count' characters from the a-z when count parameter is specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            count * nextInt(26) >>> charIndexes
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomUppercaseLetters(count).
            build()
            
        then:
        actualString == expectedOutput

        where:
        count | charIndexes | expectedOutput
        3     | [0, 13, 25] | "ANZ"
        26    | (0..25)     | "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    }
    
    def "randomNumbers should append 1 character from the 0-9 when no count parameter is specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(10) >> charIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomNumbers().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << ('0'..'9').collectWithIndex { it, idx -> [idx, it] }

    }
    
    def "randomNumbers should append 'count' characters from the 0-9 when count parameter is specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            count * nextInt(10) >>> charIndexes
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomNumbers(count).
            build()
            
        then:
        actualString == expectedOutput

        where:
        count | charIndexes | expectedOutput
        3     | [0, 3, 9]   | "039"
        10    | (0..9)      | "0123456789"

    }
    
    def "randomWhitespace should append 1 character from space or tab when no count parameter is specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(2) >> charIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomWhitespaceCharacters().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << [' ', '\t'].collectWithIndex { it, idx -> [idx, it] }

    }
    
    def "randomWhitespace should append 'count' characters from space or tab when count parameter is specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            4 * nextInt(2) >>> [1, 0, 0, 1]
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomWhitespaceCharacters(4).
            build()
            
        then:
        actualString == "\t  \t"

    }
    
    def "randomElement should append random element from list supplied"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(4) >>> elementIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomElement(["one", "two", "three", "four"]).
            build()
            
        then:
        actualString == expectedOutput

        where:
        [elementIdx, expectedOutput] << ["one", "two", "three", "four"].collectWithIndex { it, idx -> [idx, it] }
        
    }
    
    def "randomKey should append random key from list supplied"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(3) >>> elementIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomKey(["one": "foo", "two": "bar", "three": "baz"]).
            build()
            
        then:
        actualString == expectedOutput

        where:
        [elementIdx, expectedOutput] << ["one", "two", "three"].collectWithIndex { it, idx -> [idx, it] }
        
    }
    
    def "randomValue should append random value from list supplied"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(3) >>> elementIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            randomValue(["one": "foo", "two": "bar", "three": "baz"]).
            build()
            
        then:
        actualString == expectedOutput

        where:
        [elementIdx, expectedOutput] << ["foo", "bar", "baz"].collectWithIndex { it, idx -> [idx, it] }
        
    }
    
    def "oneCharacterOf should return a single character from the input string"() {
        given:
        def characters = 'stupid'
        
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(characters.length()) >> idx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            oneCharacterOf(characters).
            build()
            
        then:
        actualString == expectedCharacter

        where:
        idx | expectedCharacter
        0   | 's'
        1   | 't'
        2   | 'u'
        3   | 'p'
        4   | 'i'
        5   | 'd'
        
    }
    
    def "oneLetter should return a single letter in the a-zA-Z range"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(52) >> charIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            oneLetter().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << (('a'..'z') + ('A'..'Z')).collectWithIndex { it, idx -> [idx, it] }

    }
    
    def "oneNumber should return a single number in the 0-9 range"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(10) >> charIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            oneNumber().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << ('0'..'9').collectWithIndex { it, idx -> [idx, it] }

    }
    
    def "oneLowercaseLetter should return a single letter in the a-z range"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(26) >> charIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            oneLowercaseLetter().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << ('a'..'z').collectWithIndex { it, idx -> [idx, it] }

    }
    
    def "oneUppercaseLetter should return a single letter in the A-Z range"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(26) >> charIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            oneUppercaseLetter().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << ('A'..'Z').collectWithIndex { it, idx -> [idx, it] }

    }
    
    def "oneWhitespaceCharacter should return ' ' when randomnessProvider returns 0 or '\t' when randomnessProvider returns 1"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(2) >> charIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            oneWhitespaceCharacter().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << [' ', '\t'].collectWithIndex { it, idx -> [idx, it] }

    }
    
    def "zeroOrMoreCharactersOf should append nothing when randomnessProvider.nextInt() returns 0"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 0
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreCharactersOf('stupid').
            build()
            
        then:
        actualString == ""

    }
    
    def "zeroOrMoreCharactersOf should append up to randomUpperLimit when butNoMoreThan is not specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
            4 * nextInt(6) >>> [1, 3, 2, 4]
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreCharactersOf('stupid').
            build()
            
        then:
        actualString == "tpui"

    }

    def "zeroOrMoreCharactersOf should append up to butNoMoreThan when specified"() {
        given:
        def butNoMoreThan = 4
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(butNoMoreThan) >> 5 // how many letters to append
            5 * nextInt(6) >>> [1, 3, 2, 4, 0]
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreCharactersOf('stupid', butNoMoreThan).
            build()
            
        then:
        actualString == "tpuis"

    }

    def "zeroOrMoreLetters should append nothing when randomnessProvider.nextInt() returns 0"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 0 // how many append to return, 0 in this case
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreLetters().
            build()
            
        then:
        actualString == ""

    }
    
    def "zeroOrMoreLetters should append up to randomUpperLimit when butNoMoreThan is not specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
            4 * nextInt(52) >>> [0, 25, 26, 51]
        }

        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreLetters().
            build()
            
        then:
        actualString == "azAZ"

    }

    def "zeroOrMoreLetters should append up to butNoMoreThan when specified"() {
        given:
        def butNoMoreThan = 4
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(butNoMoreThan) >> 5 // how many letters to append
            5 * nextInt(52) >>> [0, 25, 26, 51, 1] // the letters to append
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreLetters(butNoMoreThan).
            build()
            
        then:
        actualString == "azAZb"

    }

    def "zeroOrMoreLowercaseLetters should append nothing when randomnessProvider.nextInt() returns 0"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 0 // how many append to return, 0 in this case
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreLowercaseLetters().
            build()
            
        then:
        actualString == ""

    }
    
    def "zeroOrMoreLowercaseLetters should append up to randomUpperLimit when butNoMoreThan is not specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
            4 * nextInt(26) >>> [0, 12, 13, 25]
        }

        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreLowercaseLetters().
            build()
            
        then:
        actualString == "amnz"

    }

    def "zeroOrMoreLowercaseLetters should append up to butNoMoreThan when specified"() {
        given:
        def butNoMoreThan = 4
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
            3 * nextInt(26) >>> [0, 12, 25] // the letters to append
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreLowercaseLetters(butNoMoreThan).
            build()
            
        then:
        actualString == "amz"

    }

    def "zeroOrMoreUppercaseLetters should append nothing when randomnessProvider.nextInt() returns 0"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 0 // how many append to return, 0 in this case
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreUppercaseLetters().
            build()
            
        then:
        actualString == ""

    }
    
    def "zeroOrMoreUppercaseLetters should append up to randomUpperLimit when butNoMoreThan is not specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
            4 * nextInt(26) >>> [0, 12, 13, 25]
        }

        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreUppercaseLetters().
            build()
            
        then:
        actualString == "AMNZ"

    }

    def "zeroOrMoreUppercaseLetters should append up to butNoMoreThan when specified"() {
        given:
        def butNoMoreThan = 4
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
            3 * nextInt(26) >>> [0, 12, 25] // the letters to append
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreUppercaseLetters(butNoMoreThan).
            build()
            
        then:
        actualString == "AMZ"

    }

    def "zeroOrMoreNumbers should append nothing when randomnessProvider.nextInt() returns 0"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 0 // how many append to return, 0 in this case
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreNumbers().
            build()
            
        then:
        actualString == ""

    }
    
    def "zeroOrMoreNumbers should append up to randomUpperLimit when butNoMoreThan is not specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
            4 * nextInt(10) >>> [0, 9, 6, 3]
        }

        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreNumbers().
            build()
            
        then:
        actualString == "0963"

    }

    def "zeroOrMoreNumbers should append up to butNoMoreThan when specified"() {
        given:
        def butNoMoreThan = 4
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
            3 * nextInt(10) >>> [0, 2, 4] // the letters to append
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreNumbers(butNoMoreThan).
            build()
            
        then:
        actualString == "024"

    }

    def "zeroOrMoreWhitespaceCharacters should append nothing when randomnessProvider.nextInt() returns 0"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 0 // how many append to return, 0 in this case
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreWhitespaceCharacters().
            build()
            
        then:
        actualString == ""

    }
    
    def "zeroOrMoreWhitespaceCharacters should append up to randomUpperLimit when butNoMoreThan is not specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
            4 * nextInt(2) >>> [1, 0, 0, 1]
        }

        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreWhitespaceCharacters().
            build()

        then:
        actualString == "\t  \t"

    }

    def "zeroOrMoreWhitespaceCharacters should append up to butNoMoreThan when specified"() {
        given:
        def butNoMoreThan = 4
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
            3 * nextInt(2) >>> [0, 0, 1] // the letters to append
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            zeroOrMoreWhitespaceCharacters(butNoMoreThan).
            build()
            
        then:
        actualString == "  \t"

    }

    def "atLeastOneCharacterOf should append a single character when randomnessProvider.nextInt() returns 0"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 0 // how many letters to append, up to randomUpperLimit
            1 * nextInt(6) >> charIdx 
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneCharacterOf('stupid').
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << "stupid".toList().collectWithIndex { it, idx -> [idx, it] }
        
    }
    
    def "atLeastOneCharacterOf should append up to randomUpperLimit when butNoMoreThan is not specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
            4 * nextInt(6) >>> [1, 3, 2, 4]
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneCharacterOf('stupid').
            build()
            
        then:
        actualString == "tpui"

    }

    def "atLeastOneCharacterOf should append up to butNoMoreThan when specified"() {
        given:
        def butNoMoreThan = 4
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(butNoMoreThan) >> 5 // how many letters to append
            5 * nextInt(6) >>> [1, 3, 2, 4, 0]
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneCharacterOf('stupid', butNoMoreThan).
            build()
            
        then:
        actualString == "tpuis"

    }

    def "atLeastOneLetter should append nothing when randomnessProvider.nextInt() returns 0"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 0 // how many append to return, 0 in this case
            1 * nextInt(52) >> charIdx 
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneLetter().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << (('a'..'z') + ('A'..'Z')).collectWithIndex { it, idx -> [idx, it] }
        
    }
    
    def "atLeastOneLetter should append up to randomUpperLimit when butNoMoreThan is not specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
            4 * nextInt(52) >>> [0, 25, 26, 51]
        }

        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneLetter().
            build()
            
        then:
        actualString == "azAZ"

    }

    def "atLeastOneLetter should append up to butNoMoreThan when specified"() {
        given:
        def butNoMoreThan = 4
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(butNoMoreThan) >> 5 // how many letters to append
            5 * nextInt(52) >>> [0, 25, 26, 51, 1] // the letters to append
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneLetter(butNoMoreThan).
            build()
            
        then:
        actualString == "azAZb"

    }

    def "atLeastOneLowercaseLetter should append nothing when randomnessProvider.nextInt() returns 0"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 0 // how many append to return, 0 in this case
            1 * nextInt(26) >> charIdx 
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneLowercaseLetter().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << ('a'..'z').collectWithIndex { it, idx -> [idx, it] }
        
    }
    
    def "atLeastOneLowercaseLetter should append up to randomUpperLimit when butNoMoreThan is not specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
            4 * nextInt(26) >>> [0, 12, 13, 25]
        }

        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneLowercaseLetter().
            build()
            
        then:
        actualString == "amnz"

    }

    def "atLeastOneLowercaseLetter should append up to butNoMoreThan when specified"() {
        given:
        def butNoMoreThan = 4
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
            3 * nextInt(26) >>> [0, 12, 25] // the letters to append
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneLowercaseLetter(butNoMoreThan).
            build()
            
        then:
        actualString == "amz"

    }

    def "atLeastOneUppercaseLetter should append nothing when randomnessProvider.nextInt() returns 0"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 0 // how many append to return, 0 in this case
            1 * nextInt(26) >> charIdx 
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneUppercaseLetter().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << ('A'..'Z').collectWithIndex { it, idx -> [idx, it] }
        
    }
    
    def "atLeastOneUppercaseLetter should append up to randomUpperLimit when butNoMoreThan is not specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
            4 * nextInt(26) >>> [0, 12, 13, 25]
        }

        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneUppercaseLetter().
            build()
            
        then:
        actualString == "AMNZ"

    }

    def "atLeastOneUppercaseLetter should append up to butNoMoreThan when specified"() {
        given:
        def butNoMoreThan = 4
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
            3 * nextInt(26) >>> [0, 12, 25] // the letters to append
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneUppercaseLetter(butNoMoreThan).
            build()
            
        then:
        actualString == "AMZ"

    }

    def "atLeastOneNumber should append nothing when randomnessProvider.nextInt() returns 0"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 0 // how many append to return, 0 in this case
            1 * nextInt(10) >> charIdx 
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneNumber().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << ('0'..'9').collectWithIndex { it, idx -> [idx, it] }
        
    }
    
    def "atLeastOneNumber should append up to randomUpperLimit when butNoMoreThan is not specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
            4 * nextInt(10) >>> [0, 9, 6, 3]
        }

        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneNumber().
            build()
            
        then:
        actualString == "0963"

    }

    def "atLeastOneNumber should append up to butNoMoreThan when specified"() {
        given:
        def butNoMoreThan = 4
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
            3 * nextInt(10) >>> [0, 2, 4] // the letters to append
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneNumber(butNoMoreThan).
            build()
            
        then:
        actualString == "024"

    }

    def "atLeastOneWhitespaceCharacter should append nothing when randomnessProvider.nextInt() returns 0"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 0 // how many append to return, 0 in this case
            1 * nextInt(2) >> charIdx 
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneWhitespaceCharacter().
            build()
            
        then:
        actualString == expectedOutput

        where:
        [charIdx, expectedOutput] << [' ', '\t'].collectWithIndex { it, idx -> [idx, it] }
        
    }
    
    def "atLeastOneWhitespaceCharacter should append up to randomUpperLimit when butNoMoreThan is not specified"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(25) >> 4 // how many letters to append, up to randomUpperLimit
            4 * nextInt(2) >>> [1, 0, 0, 1]
        }

        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneWhitespaceCharacter().
            build()

        then:
        actualString == "\t  \t"

    }

    def "atLeastOneWhitespaceCharacter should append up to butNoMoreThan when specified"() {
        given:
        def butNoMoreThan = 4
        
        and:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextInt(butNoMoreThan) >> 3 // how many letters to append
            3 * nextInt(2) >>> [0, 0, 1] // the letters to append
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            atLeastOneWhitespaceCharacter(butNoMoreThan).
            build()
            
        then:
        actualString == "  \t"

    }

//    def "optionalCharacterOf should append no characters when nextBoolean returns false"() {
//        given:
//        def characters = '!@#$%'
//        
//        def randomnessProvider = Mock(RandomnessProvider) {
//            1 * nextBoolean() >> false
//            0 * nextInt(characters.length())
//        }
//        
//        when:
//        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
//            value("Value").
//            optionalCharacterOf(characters).
//            build()
//            
//        then:
//        actualString == "Value"
//
//    }
//    
//    def "unspecified count parameter to optionalCharacterOf should append a single character when nextBoolean returns true"() {
//        given:
//        def characters = '!@#$%'
//        
//        def randomnessProvider = Mock(RandomnessProvider) {
//            1 * nextBoolean() >> true
//            1 * nextInt(characters.length()) >> idx
//        }
//        
//        when:
//        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
//            value("Value").
//            optionalCharacterOf(characters).
//            build()
//            
//        then:
//        actualString == "Value$expectedCharacter"
//
//        where:
//        idx | expectedCharacter
//        0   | '!'
//        1   | '@'
//        2   | '#'
//        3   | '$'
//        4   | '%'
//        
//    }
    
}
