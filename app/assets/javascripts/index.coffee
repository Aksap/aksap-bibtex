displayArticle = (index) ->
  $("article").addClass "hidden"
  $("article:eq(" + index + ")").removeClass "hidden"


$(document).ready ->
  $("header nav a").each (index) ->
    $(this).attr "id", index
    $(this).click (eventInformation) ->
      displayArticle index
      eventInformation.preventDefault()

$ ->
  $.get "../articles/show", (data) ->
    $.each data, (index, article) ->
      $('#article').append $("<li>").html "

        <b><a href='../articles/bib/"+article.id+".bib'>"+article.id+"</a></b>
        <ul><li>Title: "+article.title+"</li>
        <li>Author: "+article.author+"</li>
        <li>Journal: "+article.journal+"</li>
        <li>Year: "+article.year+"</ul><br>
        <form method='GET' value='delete' action='../articles/delete/"+article.id+"' >
              <input type='submit' value='Delete' id='article_delete'/>
        </form><br>"


$ ->
  $.get "../books/show", (data) ->
    $.each data, (index, book) ->
      $('#book').append $("<li>").html "

              <b><a href='../books/bib/"+book.id+".bib'>"+book.id+"</a></b>
              <ul><li>Title: "+book.title+"</li>
              <li>Author: "+book.author+"</li>
              <li>Publisher: "+book.publisher+"</li>
              <li>Year: "+book.year+"</ul><br>
            <form method='GET' value='delete' action='../books/delete/"+book.id+"' >
                  <input type='submit' value='Delete' id='book_delete'/>
            </form><br>"

$ ->
  $.get "show", (data) ->
    $.each data, (index, inproceeding) ->
      $('#inproceedings').append $("<li>").html "

              <b><a href='../inproceedings/bib/"+inproceeding.id+".bib'>"+inproceeding.id+"</a></b>
              <ul><li>Title: "+inproceeding.title+"</li>
              <li>Author: "+inproceeding.author+"</li>
              <li>Booktitle: "+inproceeding.booktitle+"</li>
              <li>Year: "+inproceeding.year+"</ul><br>
              <form method='GET' value='delete' action='/proceedings/delete/"+inproceeding.id+"' >
                    <input type='submit' value='Delete' id='inproceedings_delete'/>
              </form><br>"
