$(document).ready(
    function() {
      var currentType = "Users";
      var config = userConfig;
      var setCurrentType = function(type, currentConfig, currentAction) {
        return function() {
          config = currentConfig;
          currentType = type;
          
          $("#currentAction").text(currentAction);
          
          $("#currentAttributes").empty();
          $("#newAttributes").empty();
          drawComplex(config.attributes, $("#newAttributes"), $("#currentAttributes"));
          //$("#newAttributes").removeOption("");
          $('#create-result').hide();
          $('#addAttribute').unbind('click');
          $("#addAttribute").click(
              addClicked($("#newAttributes"), config.attributes,
                  $("#currentAttributes"),""));
          
          $("#filter-attribute").empty();
          $("#sort-attribute").empty();
          $("#include-attribute").empty();
          $("#included-attributes").empty();
          addFilterAttributes(config.attributes, $("#filter-attribute"));
          addFilterAttributes(config.attributes, $("#sort-attribute"));
          addFilterAttributes(config.attributes, $("#include-attribute"));
          $("#filter-attribute").sortOptions();
          $("#filter-attribute").selectOptions("");
          $("#sort-attribute").sortOptions();
          $("#sort-attribute").selectOptions("");
          $("#include-attribute").sortOptions();
          //$("#include-attribute").removeOption("");
          $('#list-result').text("");
        }
      }
      
      $("#createUser").click(setCurrentType("Users", userConfig, "Add User"));
      $("#createGroup").click(setCurrentType("Groups", groupConfig, "Add Group"));
      $("#listUsers").click(setCurrentType("Users", userConfig, "List Users"));
      $("#listGroups").click(setCurrentType("Groups", groupConfig, "List Groups"));
      $("#bulk").click(setCurrentType("Bulk", groupConfig, "Bulk"));
      
      //
      // ADD
      //
      var find = function(name, attributes) {
        for ( var i = 0; i < attributes.length; i++) {
          if (name === attributes[i].name) {
            return attributes[i];
          }
        }
      };

      var removeClicked = function(select, attribute, id) {
        return function() {
          select.addOption(attribute.name, attribute.name);
          var elements = $("#" + id);
          while (elements[0] !== undefined) {
            elements.remove();
            elements = $("#" + id);
          }
        };
      };

      
      var addClicked = function(select, attributes, current) {
        return function() {
          var attribute = find(select.val(), attributes);
          var random = Math.floor(Math.random() * 10000000);
          if (attribute.subAttributes && attribute.subAttributes.length !== 0) {
            var fieldSet = $("<fieldset></fieldset>", { id : random, name : attribute.name});
            var nextSelect = $("<select></select>", { id : random });
            fieldSet.append($("<legend></legend>", { text : attribute.name, id : random })
                .append($("<img></img>", {
              "src" : "/images/Delete-icon.png",
              "class" : "addRemoveIconSmall",
              "align" : "top",
              "id" : random
            })).click(removeClicked(select, attribute, random)));
            var fieldContainer = $("<div></div>", {
              id : random
            });
            fieldSet.append(fieldContainer);
            current.append(fieldSet);

            drawComplex(attribute.subAttributes, nextSelect, fieldContainer);

            fieldSet.append(nextSelect).append(
                $("<img></img>", {
                  "src" : "/images/Add-icon.png",
                  "class" : "addRemoveIcon",
                  "align" : "top",
                  "id" : random
                }).click(addClicked(nextSelect, attribute.subAttributes, fieldContainer)));
          } else {
            current.append($("<label></label>", {
              text : attribute.name,
              id : random
            }), $("<input></input>", {
              type : "text",
              name : attribute.name,
              disabled : attribute.readOnly,
              id : random
            }), $("<img></img>", {
              "src" : "/images/Delete-icon.png",
              "class" : "addRemoveIcon",
              "align" : "top",
              "id" : random
            }).click(removeClicked(select, attribute, random)), $("<p></p>", {
              "text" : attribute.description,
              "class" : "truncate",
              "id" : random
            }).click(toggleTruncate()));
          }
          if (attribute.multiValued !== undefined && !attribute.multiValued) {
            select.removeOption(attribute.name);
          }
        };
      };
      
      

      var toggleTruncate = function() {
        var on = true;
        return function(event) {
          if (on) {
            $(event.target).removeClass("truncate").addClass("truncateOff");
          } else {
            $(event.target).removeClass("truncateOff").addClass("truncate");
          }
          on = !on;
        }
      }

      
      var drawComplex = function(attributes, selection, current, resource) {
        for ( var i = 0; i < attributes.length; i++) {
          if (attributes[i].readOnly !== undefined) {
            if ((attributes[i].required && !attributes[i].readOnly) || 
                (resource && resource[attributes[i].name])) {
              
              if (attributes[i].type === "complex") {
                var getNext = function() {
                  var array = []
                  if (resource && attributes[i].multiValued) {
                    array = resource[attributes[i].name];
                  } else {
                    array.push(resource[attributes[i].name]);
                  }
                  var counter = 0;
                  return function() {
                    return array[counter++];
                  };
                }(); 
                
                var next = getNext();
                do {
                  var random = Math.floor(Math.random() * 10000000);
                  var fieldSet = $("<fieldset></fieldset>", {name: attributes[i].name, id : random});
                  var nextSelect = $("<select></select>",{id : random});
                  var fieldContainer = $("<div></div>",{id : random});
                  
                  drawComplex(attributes[i].subAttributes, nextSelect, fieldContainer, next);
                  
                  fieldSet.append($("<legend></legend>", { text : attributes[i].name }).append($("<img></img>", {
                    "src" : "/images/Delete-icon.png",
                    "class" : "addRemoveIconSmall",
                    "align" : "top",
                    "id" : random
                  })).click(removeClicked(selection, attributes[i], random)));
                  fieldSet.append(fieldContainer);
                  fieldSet.append(nextSelect);
                  fieldSet.append($("<img></img>", { "src" : "/images/Add-icon.png", 
                                                     "class" : "addRemoveIcon", 
                                                     "align" : "top",
                                                     "id" : random}).click(addClicked(nextSelect, attributes[i].subAttributes, fieldContainer)));
                  current.append(fieldSet);                  
                } while (next = getNext());
                
              } else {
                var random = Math.floor(Math.random() * 10000000);
                var input = $("<input></input>", { type : "text",
                                                   name : attributes[i].name,
                                                   disabled : attributes[i].readOnly,
                                                   value : (resource ? resource[attributes[i].name] : ""),
                                                   id : random});
                current.append($("<label></label>", { text : attributes[i].name,
                                                      id : random})); 
                current.append(input);
                if (resource && !attributes[i].readOnly) {
                  // this is edit track change
                  current.append($("<img>", { "src" : "/images/Delete-icon.png",
                                              "class" : "addRemoveIcon",
                                              "align" : "top",
                                              id : random}).click(removeClicked(selection, attributes[i], random)));
                }
                current.append($("<p></p>", { "text" : attributes[i].description,
                                              "class" : "truncate",
                                              id : random}).click(toggleTruncate()));
              }

              if (attributes[i].multiValued !== undefined
                  && attributes[i].multiValued) {
                selection.addOption(attributes[i].name, attributes[i].name);
              }
            } else if (!attributes[i].readOnly) {
              selection.addOption(attributes[i].name, attributes[i].name);
            }
          }
        }
      };
      drawComplex(config.attributes, $("#newAttributes"), $("#currentAttributes"));

      
      $("#addAttribute").click(
          addClicked($("#newAttributes"), config.attributes, $("#currentAttributes")));

      $("#reset").click( function() {
            $("#currentAttributes").empty();
            $("#newAttributes").empty();
            drawComplex(config.attributes, $("#newAttributes"), $("#currentAttributes"));
            $('#create-result').hide();
          });
      
      var handleAddResult = function(data) {
        $('#create-result').text(data).show();
        prettyPrint();
      };
      
      var packageResource = function(input, fieldsets) {
        var addData = { "schemas": [ "urn:scim:schemas:core:1.0" ] };
        
        var i = 0;
        while (input[i] !== undefined) {
          var val = $(input[i]).val();
          var name = $(input[i]).attr("name");
          addData[name] = val;
          i++;
        }
        
        var i = 0;
        while (fieldsets[i] !== undefined) {
          var name = $(fieldsets[i]).attr("name");
          var attribute = find(name, config.attributes);
          var container = {}
          if (attribute.multiValued) {            
            if(addData[name]) {
              addData[name].push(container);
            } else {
              addData[name] = [container];
            }
          } else {
            addData[name] = container;
          }
          
          var input = $(fieldsets[i]).find("input[type=text]");
          var j = 0;
          while (input[j] !== undefined) {
            var val = $(input[j]).val();
            var name = $(input[j]).attr("name");
            container[name] = val;
            j++;
          }
          i++
        }
        
        return addData;
      };
      
      $("#createResource").click(function () {
        var addData = packageResource($("#currentAttributes").children("input"),$("#currentAttributes fieldset"));
        
        $.post("/Viewer/Add", 
            { type: currentType, data: JSON.stringify(addData) }, 
            handleAddResult)
          .error(handleError);
      });
      
      
      //
      // LIST
      //
      var addFilterAttributes = function(attributes, select, prefix) {
        for ( var i = 0; i < attributes.length; i++) {
          if (attributes[i].subAttributes
              && attributes[i].subAttributes.length !== 0) {
            if (prefix) {
              addFilterAttributes(attributes[i].subAttributes, select, prefix
                  + "." + attributes[i].name + ".");
            } else {
              addFilterAttributes(attributes[i].subAttributes, select,
                  attributes[i].name + ".");
            }
          } else if (prefix) {
            select.addOption(prefix + attributes[i].name, prefix
                + attributes[i].name);
          } else {
            select.addOption(attributes[i].name, attributes[i].name);
          }
        }
      };

      addFilterAttributes(config.attributes, $("#filter-attribute"));
      addFilterAttributes(config.attributes, $("#sort-attribute"));
      addFilterAttributes(config.attributes, $("#include-attribute"));

      $("#addIncludeAttribute").click(function() {
        var selected = $("#include-attribute").selectedOptions();
        selected.remove();
        $("#included-attributes").append(selected);
        $("#include-attribute").sortOptions();
      });

      $("#removeIncludeAttribute").click(function() {
        var selected = $("#included-attributes").selectedOptions();
        selected.remove();
        $("#include-attribute").append(selected);
        $("#included-attributes").sortOptions();
      });
      
      $("#reset-listform").click(function() {
        $('#list-result').empty();
        
        $("#filter-attribute").empty();
        $("#sort-attribute").empty();
        $("#include-attribute").empty();
        $("#included-attributes").empty();
        addFilterAttributes(config.attributes, $("#filter-attribute"));
        addFilterAttributes(config.attributes, $("#sort-attribute"));
        addFilterAttributes(config.attributes, $("#include-attribute"));
        $("#filter-attribute").sortOptions();
        $("#filter-attribute").selectOptions("");
        $("#sort-attribute").sortOptions();
        $("#sort-attribute").selectOptions("");
        $("#include-attribute").sortOptions();
      });

      $("#filter-attribute").sortOptions();
      $("#filter-attribute").selectOptions("");
      $("#sort-attribute").sortOptions();
      $("#sort-attribute").selectOptions("");
      $("#include-attribute").sortOptions();
      
      
      
      var handleEditResult = function (data) {
        $("#list").click();
        $('#editDialog').modal("hide");
      };
      
      var editClicked = function(resource) {
        return function() {
          $("#update").click( function() {
            var patch = { "schemas": [ "urn:scim:schemas:core:1.0" ], meta:{attributes:[]} };
            var remove = patch.meta.attributes;
            var resultResource = packageResource($("#editContent").children("input"),$("#editContent fieldset"));

            var createPatch = function(before, after, patch, attributes, prefix){
            for (var i=0; i<attributes.length; i++) {
                if(!after[attributes[i].name] && !before[attributes[i].name]) {
                  // nothing nothing
                  continue;
                } else if(after[attributes[i].name] && !before[attributes[i].name]) {
                  // new attribute
                  patch[attributes[i].name] = after[attributes[i].name];
                } else if(!after[attributes[i].name] && before[attributes[i].name]) {
                  // remove attribute
                  remove.push(prefix + attributes[i].name);
                } else {
                  // both has attribute
                  if (attributes[i].type === "complex") {
                    if (attributes[i].multiValued) {
                      // TODO this is hard
                    } else {
                      // complex do recursion
                      patch[attributes[i].name] = {};
                      createPatch(before[attributes[i].name], 
                                  after[attributes[i].name], 
                                  patch[attributes[i].name],
                                  attributes[i].subAttributes,
                                  prefix + attributes[i].name + ".")
                    }
                  } else if (after[attributes[i].name] != before[attributes[i].name]) {
                    // attribute has changed update it
                    patch[attributes[i].name] = after[attributes[i].name];
                  }
                }
              }
            };
            createPatch(resource, resultResource, patch, config.attributes, "");
            
          
            var etag = ((resource.meta && resource.meta.version) ? resource.meta.version : "");
            $.post("/Viewer/Edit", 
              { type: currentType, operation: "PATCH", data: JSON.stringify(patch), id: resource.id, etag: etag }, 
              handleEditResult)
            .error(handleError);
            
            $("#update").unbind("click");
          });
          
          $("#replace").click( function() {
            var addData = packageResource($("#editContent").children("input"),$("#editContent fieldset"));
            var etag = ((resource.meta && resource.meta.version) ? resource.meta.version : "");
            
            $.post("/Viewer/Edit", { type: currentType, operation: "PUT", data: JSON.stringify(addData), id: addData.id, etag: etag }, 
                handleEditResult).error(handleError);     
            
              $("#replace").unbind("click");
          });
          
          $('#editDialog').modal();
          drawComplex(config.attributes, $('#editSelect'), $("#editContent"), resource);
        };
      };
      var handleDeleteResult = function(data) {
        $("#list").click();
      }
      var deleteClicked = function(deleteId, deleteEtag) {
        return function() {
        
        $.post("/Viewer/Delete", 
            { type: currentType, id: deleteId, etag: deleteEtag }, 
            handleDeleteResult)
          .error(handleError);
        };
      };
      
      var handleResult = function(data) {
        data = JSON.parse(data);
        $('#list-result').empty();
        
        for ( var i = 0; i < data.Resources.length; i++) {
          var meta = data.Resources[i].meta;
          var version = meta ? meta.version : undefined;
          $('#list-result')
          .append($("<div>", {"class" : "row-fluid"}).append(
                    $("<div>",{"class" : "span10"}).append(
                      $('<pre>', {"class" : 'prettyprint, full',"text" : JSON.stringify(data.Resources[i], null, "  ")})),
                    $("<div>",{"class" : "span2"}).append(
                      $("<div>", {"class":"row-fluid"}).append($("<div>",{"class" : "span12"}).append(
                        $("<img>", {"src" : "/images/edit.png","class" : "actionIcon"}).click(editClicked(data.Resources[i])))),
                      $("<div>", {"class":"row-fluid"}).append($("<div>",{"class" : "span12"}).append(
                        $("<img>", {"src" : "/images/delete-user.png","class" : "actionIcon"}).click(deleteClicked(data.Resources[i].id,version)))))));
        }

        prettyPrint();
      };

      var handleError = function(event) {
        // TODO pass error to authenticate page
        document.location = "/authenticate.html";
      };
      

      $("#list").click(function() {
        var listType = $("#listType").val();
        var theFilter = "";
        
        if ($("#filter-attribute").val() !== "" && $("#filter-attribute-value").val() !== "" ||  ($("#filter-attribute").val() !== "" && $("#filter-operation").val() == "pr")) {
          theFilter += $("#filter-attribute").val() + " ";
          theFilter += $("#filter-operation").val() + " ";
          theFilter += $("#filter-attribute-value").val();
        }
        
        var listSort = $("#sort-attribute").val();
        listSort = listSort === "" ? "": listSort;
        
        var listSortOrder = $("#sort-operation").val();
        
        var listAttributes = $("#included-attributes").children("option");
        var includeAttibutes = [];
        var i=0;
        while(listAttributes[i] !== undefined){
          includeAttibutes.push($(listAttributes[i]).val());
          i++;
        }
        var attributeList = includeAttibutes.join(",");

        $.post("/Viewer/List", {
          type : currentType,
          filter : theFilter,
          sort : listSort,
          attributes : attributeList,
          encoding : "",
          sortOrder : listSortOrder
        }, handleResult).error(handleError);
        return false;
      });
      
      
      //
      // Edit
      //
      $('#editDialog').modal();
      $('#editDialog').modal("hide");
      $('#editDialog').on('hidden', function () {
        $('#editContent').empty();
        $('#editSelect').empty();
      });
      
      $("#addEditAttribute").click(
          addClicked($("#editSelect"), config.attributes, $("#editContent")));
      
      //
      // Bulk
      //
      var handleBulkResult = function(data){
        $("#bulk-result").text(data).show();
        prettyPrint();
      };
      $("#sendBulk").click(function () {
        var bulkData = $("#bulkData").val();
        
        $.post("/Viewer/Bulk", 
            { data: bulkData }, 
            handleBulkResult)
          .error(handleError);
        
        return false;
      });
      
    });
