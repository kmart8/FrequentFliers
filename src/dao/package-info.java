/**
 * Manages server communication including connections, forming queries, and converting to/from XML.
 *
 * Queries and XML generated adhere to the format specified by the server API. Conversion of XML into java
 * primitives is also based upon server API format and uses DocumentBuilders to produce DOMs. Data access objects can
 * unpack the DOMs into Airport, Plane, and Leg objects.
 *
 * @author Chris Collins
 * @version 1.0 2020-02-24
 * @since 2020-03-06
 */
package dao;