<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<link rel="stylesheet"
	href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">

<script type="text/javascript">
	$(function() {
		$("#introduced").datepicker({
			numberOfMonths : 1,
			dateFormat : 'yy-mm-dd',
			onSelect : function(selectedDate) {
				var ds = new Date(selectedDate);
				ds.setDate(ds.getDate() + 1)
				$("#discontinued").datepicker("option", "minDate", ds);
			}
		});
		$("#discontinued").datepicker({
			numberOfMonths : 1,
			dateFormat : 'yy-mm-dd',
			onSelect : function(selectedDate) {
				var di = new Date(selectedDate)
				di.setDate(di.getDate() - 1)
				$("#introduced").datepicker("option", "maxDate", di);
			}
		});
		$("#form").validate({
			rules : {
				computerName : {
	                required: true,
	                minlength : 10
				},
				introduced: {
					required: false
				},
				discontinued: {
                    required: false
                }
			}
		});
		   $('#form input').on('keyup blur', function () { // fires on every keyup & blur
		        if ($('#form').valid()) {                   // checks form for validity
		            $('#add').prop('disabled', false);        // enables button
		        } else {
		            $('#add"').prop('disabled', 'disabled');   // disables button
		        }
		    });

	});
</script>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.jsp"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form id="form" action="" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" name="computerName"
									id="computerName" placeholder="Computer name">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" name="introduced"
									id="introduced" placeholder="Introduced date"
									required="required" >
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" name="discontinued"
									id="discontinued" placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyName">
									<option value="1">Apple Inc.</option>
									<option value="2">RCA</option>
									<option value="3">Netronics</option>
									<option value="4">Nokia</option>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" id="add" class="btn btn-primary">
							or <a href="dashboard.html" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>
