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

	def "build should return empty string when no appender calls have been made"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			0 * _(_)
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			build()
			
		then:
		actualString == ''

	}
	
    def "is should append without calling randomnessProvider"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * _(_)
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            is("Value1").
            is("Value2").
            build()
            
        then:
        actualString == 'Value1Value2'

    }
    
	def "optional should not append value when nextBoolean returns false"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> false
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value1").
			optional("Value2").
			build()
			
		then:
		actualString == 'Value1'

	}
		
	def "optional should append value when nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value1").
			optional("Value2").
			build()
			
		then:
		actualString == 'Value1Value2'

	}
		
	def "changing randomUpperLimit should modify how many characters are appended by default"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			2 * nextInt(2) >>> [1, 2]
			1 * nextInt(26) >> 12 
			2 * nextInt(10) >>> [2, 4] 
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			randomUpperLimit(2).
			atLeastOneLowercaseLetter().
			atLeastOneNumber().
			build()
			
		then:
		actualString == 'm24'

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
    
    def "oneElementOf should append random element from list supplied"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(4) >>> elementIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            oneElementOf(["one", "two", "three", "four"]).
            build()
            
        then:
        actualString == expectedOutput

        where:
        [elementIdx, expectedOutput] << ["one", "two", "three", "four"].collectWithIndex { it, idx -> [idx, it] }
        
    }
    
    def "oneKeyOf should append random key from list supplied"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(3) >>> elementIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            oneKeyOf(["one": "foo", "two": "bar", "three": "baz"]).
            build()
            
        then:
        actualString == expectedOutput

        where:
        [elementIdx, expectedOutput] << ["one", "two", "three"].collectWithIndex { it, idx -> [idx, it] }
        
    }
    
    def "oneValueOf should append random value from list supplied"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            0 * nextBoolean()
            1 * nextInt(3) >>> elementIdx
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            oneValueOf(["one": "foo", "two": "bar", "three": "baz"]).
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
        actualString == ''

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
        actualString == 'tpui'

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
        actualString == 'tpuis'

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
        actualString == ''

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
        actualString == 'azAZ'

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
        actualString == 'azAZb'

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
        actualString == ''

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
        actualString == 'amnz'

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
        actualString == 'amz'

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
        actualString == ''

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
        actualString == 'AMNZ'

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
        actualString == 'AMZ'

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
        actualString == ''

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
        actualString == '0963'

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
        actualString == '024'

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
        actualString == ''

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
        actualString == '\t  \t'

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
        actualString == '  \t'

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
        actualString == 'tpui'

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
        actualString == 'tpuis'

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
        actualString == 'azAZ'

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
        actualString == 'azAZb'

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
        actualString == 'amnz'

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
        actualString == 'amz'

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
        actualString == 'AMNZ'

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
        actualString == 'AMZ'

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
        actualString == '0963'

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
        actualString == '024'

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
        actualString == '\t  \t'

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
        actualString == '  \t'

    }

    def "optionalCharactersOf should append no characters when nextBoolean returns false"() {
        given:
        def characters = '!@#$%'
        
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextBoolean() >> false
            0 * nextInt(characters.length())
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            is("Value").
            optionalCharactersOf(characters).
            build()
            
        then:
        actualString == 'Value'

    }
    
    def "optionalLetters should append no characters when nextBoolean returns false"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            is("Value").
            optionalLetters().
            build()
            
        then:
        actualString == 'Value'

    }
    
    def "optionalNumbers should append no characters when nextBoolean returns false"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            is("Value").
            optionalNumbers().
            build()
            
        then:
        actualString == 'Value'

    }
    
    def "optionalLowercaseLetters should append no characters when nextBoolean returns false"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            is("Value").
            optionalLowercaseLetters().
            build()
            
        then:
        actualString == 'Value'

    }
    
    def "optionalUppercaseLetters should append no characters when nextBoolean returns false"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            is("Value").
            optionalUppercaseLetters().
            build()
            
        then:
        actualString == 'Value'

    }
    
    def "optionalWhitespaceCharacters should append no characters when nextBoolean returns false"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            is("Value").
            optionalWhitespaceCharacters().
            build()
            
        then:
        actualString == 'Value'

    }
    
    def "optionalElementOf should append nothing when nextBoolean returns false"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            is("Value").
            optionalElementOf(["one", "two", "three"]).
            build()
            
        then:
        actualString == 'Value'

    }
    
    def "optionalKeyOf should append nothing when nextBoolean returns false"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            is("Value").
            optionalKeyOf(["one" : 1, "two" : 2, "three" : 3]).
            build()
            
        then:
        actualString == 'Value'

    }
    
    def "optionalValueOf should append nothing when nextBoolean returns false"() {
        given:
        def randomnessProvider = Mock(RandomnessProvider) {
            1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
        }
        
        when:
        def actualString = new RandomStringGenerator.Builder(randomnessProvider).
            is("Value").
            optionalValueOf(["one" : 1, "two" : 2, "three" : 3]).
            build()
            
        then:
        actualString == 'Value'

    }
	
	def "optionalCharactersOf should append up to randomUpperLimit when butNoMoreThan is not specified and nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(25) >> 3 // how many letters to append
			3 * nextInt(5) >>> [3, 1, 2] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalCharactersOf('!@#$%').
			build()
			
		then:
		actualString == 'Value$@#'

	}
	
	def "optionalLetters should append up to randomUpperLimit when butNoMoreThan is not specified and nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(25) >> 4 // how many letters to append
            4 * nextInt(52) >>> [0, 25, 26, 51] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalLetters().
			build()
			
		then:
		actualString == 'ValueazAZ'

	}
    
	def "optionalNumbers should append up to randomUpperLimit when butNoMoreThan is not specified and nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(25) >> 3 // how many numbers to append
            3 * nextInt(10) >>> [8, 4, 6] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalNumbers().
			build()
			
		then:
		actualString == 'Value846'

	}
    
	def "optionalLowercaseLetters should append up to randomUpperLimit when butNoMoreThan is not specified and nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(25) >> 4 // how many letters to append
            4 * nextInt(26) >>> [0, 12, 13, 25] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalLowercaseLetters().
			build()
			
		then:
		actualString == 'Valueamnz'

	}
    
	def "optionalUppercaseLetters should append up to randomUpperLimit when butNoMoreThan is not specified and nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(25) >> 4 // how many letters to append
            4 * nextInt(26) >>> [0, 12, 13, 25] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalUppercaseLetters().
			build()
			
		then:
		actualString == 'ValueAMNZ'

	}
    
	def "optionalWhitespaceCharacters should append up to randomUpperLimit when butNoMoreThan is not specified and nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(25) >> 4 // how many whitespace chars to append
            4 * nextInt(2) >>> [0, 1, 1, 0] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalWhitespaceCharacters().
			build()
			
		then:
		actualString == 'Value \t\t '

	}
 
	def "optionalElementOf should append 1 element of the supplied list when nextBoolean return true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(4) >> elementIdx
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			optionalElementOf(["one", "two", "three", "four"]).
			build()
			
		then:
		actualString == expectedOutput

		where:
		[elementIdx, expectedOutput] << ["one", "two", "three", "four"].collectWithIndex { it, idx -> [idx, it] }

	}
	   
	def "optionalKeyOf should append 1 key of the supplied map when nextBoolean return true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(3) >> elementIdx
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			optionalKeyOf(["one": "foo", "two": "bar", "three": "baz"]).
			build()
			
		then:
		actualString == expectedOutput

		where:
		[elementIdx, expectedOutput] << ["one", "two", "three"].collectWithIndex { it, idx -> [idx, it] }

	}
	   
	def "optionalValueOf should append 1 value of the supplied map when nextBoolean return true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(3) >> elementIdx
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			optionalValueOf(["one": "foo", "two": "bar", "three": "baz"]).
			build()
			
		then:
		actualString == expectedOutput

		where:
		[elementIdx, expectedOutput] << ["foo", "bar", "baz"].collectWithIndex { it, idx -> [idx, it] }

	}
	 
	def "optionalCharactersOf should append up to butNoMoreThan when specified and nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(4) >> 3 // how many letters to append
			3 * nextInt(5) >>> [3, 1, 2] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalCharactersOf('!@#$%', 4).
			build()
			
		then:
		actualString == 'Value$@#'

	}
		  
	def "optionalLetters should append up to butNoMoreThan when specified and nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(17) >> 4 // how many letters to append
            4 * nextInt(52) >>> [0, 25, 26, 51] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalLetters(17).
			build()
			
		then:
		actualString == 'ValueazAZ'

	}
    
	def "optionalNumbers should append up to butNoMoreThan when specified and nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(17) >> 3 // how many numbers to append
            3 * nextInt(10) >>> [8, 4, 6] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalNumbers(17).
			build()
			
		then:
		actualString == 'Value846'

	}
    
	def "optionalLowercaseLetters should append up to butNoMoreThan when specified and nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(17) >> 4 // how many letters to append
            4 * nextInt(26) >>> [0, 12, 13, 25] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalLowercaseLetters(17).
			build()
			
		then:
		actualString == 'Valueamnz'

	}
    
	def "optionalUppercaseLetters should append up to butNoMoreThan when specified and nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(17) >> 4 // how many letters to append
            4 * nextInt(26) >>> [0, 12, 13, 25] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalUppercaseLetters(17).
			build()
			
		then:
		actualString == 'ValueAMNZ'

	}
    
	def "optionalWhitespaceCharacters should append up to butNoMoreThan when specified and nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(17) >> 4 // how many whitespace chars to append
            4 * nextInt(2) >>> [0, 1, 1, 0] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalWhitespaceCharacters(17).
			build()
			
		then:
		actualString == 'Value \t\t '

	}

	def "optionalCharacterOf should append 0 characters when nextBoolean returns false"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalCharacterOf('!@#$%').
			build()
			
		then:
		actualString == 'Value'

	}
		  
	def "optionalLetter should append 0 letters when nextBoolean returns false"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalLetter().
			build()
			
		then:
		actualString == 'Value'

	}
    
	def "optionalNumber should append 0 numbers when nextBoolean returns false"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalNumber().
			build()
			
		then:
		actualString == 'Value'

	}
    
	def "optionalLowercaseLetter should append 0 lowercase letters when nextBoolean returns false"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalLowercaseLetter().
			build()
			
		then:
		actualString == 'Value'

	}
    
	def "optionalUppercaseLetter should append 0 uppercase letters when nextBoolean returns false"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalUppercaseLetter().
			build()
			
		then:
		actualString == 'Value'

	}
    
	def "optionalWhitespaceCharacter should append 0 whitespace characters when nextBoolean returns false"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> false
            0 * nextInt(_ as Integer)
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			is("Value").
			optionalWhitespaceCharacter().
			build()
			
		then:
		actualString == 'Value'

	}

	def "optionalCharacterOf should append 1 character when nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
			1 * nextInt(5) >> 3 // the character index to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			optionalCharacterOf('!@#$%').
			build()
			
		then:
		actualString == '$'

	}
		  
	def "optionalLetter should append up 1 letter when nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
            1 * nextInt(52) >> 12 // the character index to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			optionalLetter().
			build()
			
		then:
		actualString == 'm'

	}
    
	def "optionalNumber should append 1 number nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
            1 * nextInt(10) >> 8 // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			optionalNumber().
			build()
			
		then:
		actualString == '8'

	}
    
	def "optionalLowercaseLetter should append 1 lowercase letter nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
            1 * nextInt(26) >> 12 // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			optionalLowercaseLetter().
			build()
			
		then:
		actualString == 'm'

	}
    
	def "optionalUppercaseLetter should append 1 uppercase letter nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
            1 * nextInt(26) >> 12 // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			optionalUppercaseLetter().
			build()
			
		then:
		actualString == 'M'

	}
    
	def "optionalWhitespaceCharacter should append 1 whitespace character nextBoolean returns true"() {
		given:
		def randomnessProvider = Mock(RandomnessProvider) {
			1 * nextBoolean() >> true
            1 * nextInt(2) >> 0 // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			optionalWhitespaceCharacter().
			build()
			
		then:
		actualString == ' '

	}

	def "charactersOf should append exactly n characters from the supplied string"() {
		given:
		def n = 4
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			n * nextInt(5) >>> [2, 0, 1, 4] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			nCharactersOf('!@#$%', n).
			build()
			
		then:
		actualString == '#!@%'

	}
	
	def "letters should append exactly n letters"() {
		given:
		def n = 7 
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			n * nextInt(52) >>> [12, 13, 24, 25, 1, 2, 51] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			nLetters(n).
			build()
			
		then:
		actualString == 'mnyzbcZ'

	}
	
	def "numbers should append exactly n numbers"() {
		given:
		def n = 3
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			n * nextInt(10) >>> [2, 4, 7] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			nNumbers(n).
			build()
			
		then:
		actualString == '247'

	}
	
	def "lowercaseLetters should append exactly n uppercase letters"() {
		given:
		def n = 6
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			n * nextInt(26) >>> [12, 13, 24, 25, 1, 2] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			nLowercaseLetters(n).
			build()
			
		then:
		actualString == 'mnyzbc'


	}
	
	def "uppercaseLetters should append exactly n lowercase letters"() {
		given:
		def n = 6
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			n * nextInt(26) >>> [12, 13, 24, 25, 1, 2, 51] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			nUppercaseLetters(n).
			build()
			
		then:
		actualString == 'MNYZBC'


	}
	
	def "whitespaceCharacters should append exactly n whitespace characters"() {
		given:
		def n = 6
		
		and:
		def randomnessProvider = Mock(RandomnessProvider) {
			n * nextInt(2) >>> [0, 1, 1, 0, 1, 0] // the character indexes to append
		}
		
		when:
		def actualString = new RandomStringGenerator.Builder(randomnessProvider).
			nWhitespaceCharacters(n).
			build()
			
		then:
		actualString == ' \t\t \t '
		
	}
	
}
