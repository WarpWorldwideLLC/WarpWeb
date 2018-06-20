/*!
 * warp_util v1.0.0 
 * WARP Utility Functions
 * 
 */

function testFunction() {
	alert("testFunction Runnng!!");
}

function getClientDateTime() {
	  var date = new Date();
	  var hours = date.getHours();
	  var minutes = date.getMinutes();
	  var ampm = hours >= 12 ? 'pm' : 'am';
	  hours = hours % 12;
	  hours = hours ? hours : 12; // the hour '0' should be '12'
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  var strTime = hours + ':' + minutes + ' ' + ampm;
	  // return date.getMonth()+1 + "/" + date.getDate() + "/" + date.getFullYear() + "  " + strTime;	  
	  var returnValue = date.getMonth()+1 + "/" + date.getDate() + "/" + date.getFullYear() + "  " + strTime;	  ;
	  return returnValue;
}

function getClientTimeZone() {
	var d = new Date();
	var tz = d.toString().split("GMT")[1].split(" (")[0]; // timezone, i.e. -0700
	var tzName = d.toString().split("GMT")[1]; // timezone, i.e. -0700 (Pacific Daylight Time)
	
}

function toggleHide(targetElement)
{

	var att = document.getElementById(targetElement).getAttribute('type');

	if(att == 'password')
		{
			document.getElementById(targetElement).setAttribute('type', 'text');
		}
	else 
		{
		document.getElementById(targetElement).setAttribute('type', 'password');
		}
}


function toggleHide2(inputValue1, inputValue2)
{
	toggleHide(inputValue1);
	toggleHide(inputValue2);
}

//************************************************************************************************************************************************************************
//*** Table Buider
//************************************************************************************************************************************************************************

	// List Elements of an array
	function showArray(objArr) {
		$.each(objArr, function(index, element) {
			alert("ArrayIndex: " + index + " ArrayElement: " + element);
		}) 
				
	}
	
	// List properties of an object
	function showObject(obj) {
			alert(JSON.stringify(obj));
		  $.each(obj, function(objectProperty, objectValue) {
			alert("PropertyName: " + objectProperty + " PropertyValue: " + objectValue)
		  });
		}
	
	
	// Build HTML Table from JSON input. 
	function warpBuildHtmlTable(selector) {
		alert ('Table Building Begins!');
		jsonData = JSON.parse(myList);
		
		var columnList = warpAddAllColumnHeaders(selector, jsonData);
		
		$.each(jsonData, function(arrayIndex, arrayValue) {
			var row$ = $('<tr>');
			// Pull arrayValue for each corresponding ColumnValue to ensure Table Header and Table Data align properly.
			$.each(columnList, function(columnIndex, columnValue) {
				var cellValue = arrayValue[columnValue];
				if(cellValue == null) cellValue == "";
				row$.append($('<td/>').html(cellValue));
			});
			$(selector).append(row$);
		});
	}
	
	// Adds a header row to the table and returns the set of columns.
	// Need to do union of keys from all records as some records may not contain
	// all records.
	function warpAddAllColumnHeaders (selector, jsonData) {
		var columnSet = [];
		var headerTr$ = $('<tr/>');
		
		$.each(jsonData, function(arrayIndex, arrayValue) {
			var rowHash = arrayValue;
			$.each(arrayValue, function(elementName, elementValue) {
				if ($.inArray(elementName, columnSet) == -1) 
			      {
			        columnSet.push(elementName);
			        headerTr$.append($('<th/>').html(elementName));
			      }
			});
			
		});
		$(selector).append(headerTr$);
		return columnSet;
	}
	
	
	// Builds the HTML Table out of myList.
	function buildHtmlTable(selector) {
	alert('Building Table');
	  var columns = addAllColumnHeaders(myList, selector);

	  for (var i = 0; i < myList.length; i++) {
	    var row$ = $('<tr/>');
	    for (var colIndex = 0; colIndex < columns.length; colIndex++) {
	      var cellValue = myList[i][columns[colIndex]];
	      if (cellValue == null) cellValue = "";
	      row$.append($('<td/>').html(cellValue));
	    }
	    $(selector).append(row$);
	  }
	}

	// Adds Header List to HTML Table
	function addAllColumnHeaders(myList, selector) {
	  var columnSet = [];
	  var headerTr$ = $('<tr/>');
	  
	  for (var i = 0; i < myList.length; i++) {
	    var rowHash = myList[i];
	    for (var key in rowHash) {
	      if ($.inArray(key, columnSet) == -1) 
	      {
	        columnSet.push(key);
	        headerTr$.append($('<th/>').html(key));
	      }
	    }
	  }
	  $(selector).append(headerTr$);

	  return columnSet;
	}

	
	/* *************************************************************/
	/*             The side navigation menu                        */
	/* *************************************************************/
	
	/* Set the width of the side navigation to 250px and the left margin of the page content to 250px */
	function openNav() {
	    document.getElementById("mySidenav").style.width = "250px";
	    document.getElementById("main").style.marginLeft = "250px";
	}

	/* Set the width of the side navigation to 0 and the left margin of the page content to 0 */
	function closeNav() {
	    document.getElementById("mySidenav").style.width = "0";
	    document.getElementById("main").style.marginLeft = "0";
	}
	