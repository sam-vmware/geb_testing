package utils

import groovy.transform.TupleConstructor
import groovy.util.logging.Log

/*
 * @author samueldoyle
 */
@Singleton
@TupleConstructor
@Log
class BasicTestUtils {
    final ConfigObject configObject

    private BasicTestUtils() {
        def propertyClass = BasicTestUtils.class.classLoader.loadClass("FunctionalTestsConfig")
        configObject = (new ConfigSlurper()).parse(propertyClass)
    }

    public def getConfigProperty(PropertyMappingEnum... properties) {
         assert properties : "Required propertyPath(s)"
         def returnResult = []

         returnResult = properties.collect { p ->
             p.propertyPath.split("\\.").inject(configObject) { root, nextNode ->
                 root."$nextNode"
             }
         }

         if (!returnResult) {
             log.warning "Couldn't locate property matching: $properties"
         }

         return returnResult.size() == 1 ? returnResult[0] : returnResult
     }
}
