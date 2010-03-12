<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xslthl="http://xslthl.sf.net"
	version="1.0">

	<xsl:import href="urn:docbkx:stylesheet" />

	<xsl:import href="highlight.xsl" />


	<xsl:param name="admon.graphics" select="1" />
	<xsl:param name="highlight.source" select="1" />

	<xsl:template match='xslthl:keyword'>
		<fo:inline font-weight="bold" color="#7F0055">
			<xsl:apply-templates />
		</fo:inline>
	</xsl:template>

	<xsl:template match='xslthl:comment'>
		<fo:inline font-style="italic" color="#3F7F5F">
			<xsl:apply-templates />
		</fo:inline>
	</xsl:template>

	<xsl:template match='xslthl:string'>
		<fo:inline color="blue">
			<xsl:apply-templates />
		</fo:inline>
	</xsl:template>


	<xsl:attribute-set name="section.title.level1.properties">
		<xsl:attribute name="font-size">
          <xsl:value-of select="$body.font.master * 1.8" />
          <xsl:text>pt</xsl:text>
        </xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>

	</xsl:attribute-set>

	<xsl:attribute-set name="section.title.level2.properties">
		<xsl:attribute name="font-size">
          <xsl:value-of select="$body.font.master * 1.3" />
          <xsl:text>pt</xsl:text>
        </xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>

	</xsl:attribute-set>

	<xsl:attribute-set name="section.title.level3.properties">
		<xsl:attribute name="font-size">
          <xsl:value-of select="$body.font.master * 1.1" />          
          <xsl:text>pt</xsl:text>
        </xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>

	</xsl:attribute-set>


	<xsl:attribute-set name="component.title.properties">
		<xsl:attribute name="font-size">
          <xsl:value-of select="$body.font.master * 2" />
          <xsl:text>pt</xsl:text>
        </xsl:attribute>
        <xsl:attribute name="border-bottom-style">solid</xsl:attribute>

	</xsl:attribute-set>
	
	<xsl:attribute-set name="section.title.properties">
		<xsl:attribute name="border-bottom-style">solid</xsl:attribute>
	</xsl:attribute-set>
		

	<xsl:attribute-set name="monospace.verbatim.properties">
		<xsl:attribute name="font-size">8pt</xsl:attribute>
		<xsl:attribute name="keep-together.within-column">always</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="shade.verbatim.properties">
		<xsl:attribute name="border-color">thin black ridge</xsl:attribute>
		<xsl:attribute name="background-color">silver</xsl:attribute>
	</xsl:attribute-set>



</xsl:stylesheet>

