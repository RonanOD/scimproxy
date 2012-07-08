$(document).ready(
    function() {
      var getColor = function() {
        var counter = 1;
        return function() {
          counter++;
          return (counter % 2) === 0 ? "white" : "";
        }
      };

      var getIndex = function() {
        var counter = 1;
        var index = 0
        return function() {
          counter++;
          if ((counter % 2) === 0) {
            index++;
          }
          return index;
        }
      };

      var toggleWire = function(event) {
        if ($(event.currentTarget).find(".arrow")
            .hasClass('icon-chevron-right')) {
          $(event.currentTarget).find(".arrow").removeClass(
              "icon-chevron-right");
          $(event.currentTarget).find(".arrow").addClass("icon-chevron-down");
          $("#" + $(event.currentTarget).attr("index")).slideDown("slow");
        } else {
          $(event.currentTarget).find(".arrow")
              .removeClass("icon-chevron-down");
          $(event.currentTarget).find(".arrow").addClass("icon-chevron-right");
          $("#" + $(event.currentTarget).attr("index")).slideUp("slow");
        }
      };

      var handleError = function(event) {
        $("#spinner-container").empty();
        $("#compliance-error").text("An error occurred. " + event.statusText);
        $("#compliance-error-container").show();
      };

      var handleResponse = function(data) {
        $("#spinner-container").empty();

        if (data.authRequired === "true") {
          // TODO filter methods
          $('#authenticationDialog').modal("show");
        } else {
          data.index = getIndex();
          data.color = getColor();
          var template = $('#testTemplate').html();
          var html = Mustache.to_html(template, data);
          $("#compliance-result-container").html(html);
          $(".label-info").click(toggleWire);
          $("[rel=tooltip]").tooltip();

          var success = parseInt(data.statistics.success);
          var failed = parseInt(data.statistics.failed);
          template = $('#statisticsTemplate').html();
          html = Mustache.to_html(template, {
            total : failed + success,
            success : success,
            failed : failed
          });
          $("#compliance-statistics-text").html(html);

          var options = {
            legend : 'none',
            chartArea : {
              width : "100%",
              height : "100%"
            },
            colors : [ 'darkgrey', 'lightgrey' ],
            backgroundColor : "whiteSmoke"
          };
          var chartData = google.visualization.arrayToDataTable([
              [ 'Result', 'Number' ], [ 'Failed', failed ],
              [ 'Success', success ] ]);
          new google.visualization.PieChart(document
              .getElementById('compliance-chart')).draw(chartData, options);

          $("#result-container").show();
        }
      };

      var authMethodChanged = function() {
        $("#authMethod option:selected").each(function() {
          $(".authMethod").hide();
          $("#" + $(this).val()).show();
        });
      };

      var sendRequest = function() {
        var data = {
          url : $("#complianceUrl").val(),
          authMethod : $("#authMethod option:selected").val(),
          username : $("#username").val(),
          password : $("#password").val(),
          clientId : $("#oauthClientId").val(),
          clientSecret : $("#oauthClientSecret").val(),
          authorizationServer : $("#oauthAuthorizationServer").val()
        };

        $('#authenticationDialog').modal("hide");
        $("#result-container").hide();
        $("#compliance-error").hide();

        $("#spinner-container").spin({
          lines : 13,
          length : 30,
          width : 10,
          radius : 40,
          rotate : 0,
          color : '#000',
          speed : 1,
          trail : 60,
          shadow : false,
          hwaccel : false,
          className : 'spinner',
          zIndex : 2e9,
          top : 'auto',
          left : 'auto'
        });
        $.post("/compliance/test", data, handleResponse);// .error(handleError);

        $("#username").val("");
        $("#password").val("");
        $("#oauthClientId").val("");
        $("#oauthClientSecret").val("");
        $("#oauthAuthorizationServer").val("");
        return false;
      };

      $("#sendCompliance").click(sendRequest);
      $("#continueWithAuth").click(sendRequest);
      $("#authMethod").change(authMethodChanged).change();
    });
