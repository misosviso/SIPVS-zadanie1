<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
<xsl:template match="/form">
    <html>

    <head>
        <meta charset="utf-8" />
        <title>Spring Boot Web App using Spring MVC and Spring Data JPA</title>
    </head>

    <body>
        <h1>Spring Boot Web App using Spring MVC and Spring Data JPA</h1>
        <hr />
        
        <p>First Name: <input type="text" disabled="true" value="{//first-name}"/></p>
       <p>Last Name: <input type="text" disabled="true" value="{//last-name}"/></p>
        <p>Email Address: <input type="text" disabled="true" value="{//email-address}"/></p>
        <p>Restaurant Address: <input type="text" disabled="true" value="{//restaurant-address}"/></p>
        <p>Restaurant Rating: <input type="text" disabled="true" value="{//restaurant-rating}"/></p>
        <p>Restaurant Comment: <input type="text" disabled="true" value="{//restaurant-comment}"/></p>
        <p>Visit Date: <input type="date" disabled="true" value="{//visit-date}"/></p>
    </body>

    </html>
</xsl:template>
</xsl:stylesheet>