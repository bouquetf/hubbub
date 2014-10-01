<%@ page import="com.grailsinaction.User" %>
<html>
<head>
    <title>Search Results</title>
    <meta name="layout" content="main" />
</head>

<body>
    <h1>Results</h1>

    <p>
        Searched ${User.count()} records for items matching <em>${term}</em>.
        Found <strong>${users.size()}</strong> hits.
    </p>

<ul>
    <g:each in="${users}" var="user">
        <li>${user.userId}</li>
    </g:each>
</ul>

<g:link action="search">Search again</g:link>

</body>
</html>