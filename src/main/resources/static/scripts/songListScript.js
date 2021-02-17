$(document).ready(function(){
    var button = document.getElementsByClassName("commentsButton");
    var i;
    for (i = 0; i < button.length; i++) {
        button[i].addEventListener("click", function() {
            var content = this.nextElementSibling;
            this.classList.toggle("active");
            if (content.style.display === "block") {

                content.style.display = "none";
            } else {
                content.style.display = "block";

            }
        });
    }
    $('.comment-edit-button').on('click', function(event){
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href,function(comment){
            $('#editCommentModal #commentId').val(comment.id);
            $('#editCommentModal #content').val(comment.content);
            $('#editCommentModal #delete-comment-button').attr('href','/comment/delete/'.concat(comment.displayProfileUsername,'/',comment.id));

        });
        $('#editCommentModal').modal('show');
    });


});