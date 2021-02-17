$(document).ready(function(){
    $('.editSongButton').on('click', function(event){
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href,function(song,status){
            $('#updateSongModal #id').val(song.id);
            $('#updateSongModal #name').val(song.name);
            $('#updateSongModal #artist').val(song.artist);
            $('#updateSongModal #songUrl').val(song.songUrl);
            $('#updateSongModal #delete').attr('href','/music/delete/'.concat(song.id));
        });
       $('#updateSongModal').modal('show');
    });


});

$("#uploadSongForm").validate();
$("#updateSongForm").validate();