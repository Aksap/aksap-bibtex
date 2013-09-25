$ ->
  $.get "show", (data) ->
    $.each data, (index, inproceeding) ->
      $('#inproceedings').append $("<li>").html "

        <b>Title: </b>"+inproceeding.title+"
        <ul><li>Author: "+inproceeding.author+"</li>
        <li>Booktitle: "+inproceeding.booktitle+"</li>
        <li>Year: "+inproceeding.year+"<br><br>"