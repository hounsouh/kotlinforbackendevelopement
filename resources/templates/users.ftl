<#-- @ftlvariable name="customers" type="hos.houns.model.Customers" -->
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>KDays</title>
</head>
<body>
<table class="table">
    <thead class="thead-light">
    <tr>
        <th scope="col">User ID</th>
        <th scope="col">First</th>
        <th scope="col">Last</th>
        <th scope="col">Actions</th>
    </tr>
    </thead>
    <tbody>
    <#list customers.list as customer>
        <tr>
            <th scope="row">${customer.userId}</th>
            <td>${customer.firstName}</td>
            <td>${customer.lastName}</td>
            <td>
                <form method="post" action="customers">
                    <input type="hidden" name="userId" value="${customer.userId}"/>
                    <input type="hidden" name="action" value="delete"/>
                    <button type="submit" title="Delete">Delete</button>
                </form>
            </td>
        </tr>
    </#list>
    </tbody>
</table>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>