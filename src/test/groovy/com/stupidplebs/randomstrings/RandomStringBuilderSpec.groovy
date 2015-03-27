package com.stupidplebs.randomstrings

import com.stupidplebs.randomstrings.provider.RandomnessProvider;

import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges

@ConfineMetaClassChanges(List)
class RandomStringBuilderSpec extends Specification {
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
		def actualString = new RandomStringBuilder(randomnessProvider).
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
        def actualString = new RandomStringBuilder(randomnessProvider).
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
		def actualString = new RandomStringBuilder(randomnessProvider).
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
		def actualString = new RandomStringBuilder(randomnessProvider).
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
			1 * nextInt(RandomStringBuilder.LOWERCASE_LETTERS.length()) >> 12 
			2 * nextInt(RandomStringBuilder.NUMBERS.length()) >>> [2, 4] 
		}
		
		when:
		def actualString = new RandomStringBuilder(randomnessProvider).
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
        def actualString = new RandomStringBuilder(randomnessProvider).
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
        def actualString = new RandomStringBuilder(randomnessProvider).
            randomCharactersOf(characters, count).
            build()
            
        then:
        actualString == expectedString
        
        where:
        count | charIndexes       | expectedString
        3     | [1, 3, 5]         | "tpd"
        5     | [1, 3, 5, 0, 1]   | "tpdst"
        
    }
    
}
