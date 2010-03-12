<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:xslthl="http://xslthl.sf.net" version="1.0">

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
	


</xsl:stylesheet>

