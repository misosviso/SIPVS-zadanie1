<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns="http://sipvs-projekt.com/sipvs-namespace"
    xmlns:tns="http://sipvs-projekt.com/sipvs-namespace"
    exclude-result-prefixes="tns">

    <xsl:template match="/tns:form">
        <html>
            <head>
                <meta charset="utf-8" />
                <title>Review Form</title>
                <style>
                    .ratings {
                    border: 1px solid #ccc;
                    padding: 10px;
                    margin-bottom: 10px;
                    }

                    .rating {
                    border: 1px solid #ccc;
                    padding: 10px;
                    margin-bottom: 10px;
                    }
                </style>
            </head>

            <body>
                <h1>Restaurant Review Form</h1>
                <hr />

                <p>First Name: <input type="text" disabled="true" value="{tns:first-name}" /></p>
                <p>Last Name: <input type="text" disabled="true" value="{tns:last-name}" /></p>
                <p>Email Address: <input type="text" disabled="true" value="{tns:email-address}" /></p>
                <xsl:apply-templates select="tns:ratings" />
            </body>

        </html>
    </xsl:template>

    <xsl:template match="tns:ratings">
        <div class="ratings">
            <xsl:apply-templates select="tns:rating" />
        </div>
    </xsl:template>

    <xsl:template match="tns:rating">
        <div class="rating">
            <p>Restaurant Address: <input type="text" disabled="true"
                    value="{tns:restaurant-address}" /></p>
            <p>Restaurant Rating: <input type="integer" disabled="true"
                    value="{tns:restaurant-rating}" /></p>
            <p>Restaurant Comment: <input type="text" disabled="true"
                    value="{tns:restaurant-comment}" /></p>
            <p>Visit Date: <input type="date" disabled="true" value="{tns:visit-date}" /></p>
        </div>
    </xsl:template>

</xsl:stylesheet>