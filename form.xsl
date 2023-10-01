<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
<xsl:template match="/form">
    <html>
        <head>
            <meta charset="utf-8" />
            <title>Review form</title>
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
            <h1>Restaurant Review form</h1>
            <hr />
            
            <p>First Name: <input type="text" disabled="true" value="{first-name}"/></p>
            <p>Last Name: <input type="text" disabled="true" value="{last-name}"/></p>
            <p>Email Address: <input type="text" disabled="true" value="{email-address}"/></p>
            <xsl:apply-templates select="ratings"/>
        </body>

    </html>
</xsl:template>

<xsl:template match="ratings">
    <div class="ratings">
        <xsl:apply-templates select="rating"/>
    </div>
</xsl:template>

<xsl:template match="rating">
    <div class="rating">
        <p>Restaurant Address: <input type="text" disabled="true" value="{restaurant-address}"/></p>
        <p>Restaurant Rating: <input type="integer" disabled="true" value="{restaurant-rating}"/></p>
        <p>Restaurant Comment: <input type="text" disabled="true" value="{restaurant-comment}"/></p>
        <p>Visit Date: <input type="date" disabled="true" value="{visit-date}"/></p>
    </div>
</xsl:template>

</xsl:stylesheet>