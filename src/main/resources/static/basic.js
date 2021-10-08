let postid = 0;

function detailBoard(id) {
    let status = $('.showList').css('display');
    if (status == 'block'){
        $('.showList').hide();
    }else{
        $('.readDetail').show();
    }

    let status2 = $('#topMenu').css('display');
    if (status2 == 'none'){
        $('#topMenu').show();
    }else{
        $('#topMenu').hide();
    }

    let status3 = $('.area-write').css('display');
    if (status3 == 'none'){
        $('.showList').hide();
    }else{
        $('.area-write').show();
    }

    $.ajax({
        type: 'GET',
        url: `/api/boards/${id}`,
        success: function (response) {
            $('#commentinput').empty();
            console.log(response)
            postid = id;
            $('#detailTitle').text(response['title'])
            $('#detailUsername').text(response['username'])
            $('#detailContents').text(response['contents'])
            $('#detailTime').text(response['modifiedAt'])
            // window.location.href='detail.html'
            // 댓글에 대한 for문
            let htmltemp = `<textarea class="field" placeholder="댓글을 입력해주세요" name="comment" id="comment" cols="30"
                                          rows="3"></textarea>
                            <button class="btn btn-primary" type="button" onclick="writeComment()">댓글쓰기</button>`
            $('#commentinput').append(htmltemp);
            for (let i = 0; i < response['commentList'].length; i++){
                let comment = response['commentList'][i]['comment']
                console.log(comment);
                addComment(comment,response['commentList'][i]['id']);
            }
        }
    })
}

function addComment(comment, id) {
    // 1. HTML 태그를 만듭니다.
    let tempHtml = `<textarea name="commentlists" id="${id}" cols="30"
              rows="3">${comment}</textarea><br>
              <button class="btn btn-primary" type="button" onclick="updateComment(${id})">수정</button>
              <button class="btn btn-primary" type="button" onclick="deleteCommnent(${id})">삭제</button><br>`;
    // 2. #cards-box 에 HTML을 붙인다.
    $('#commentlist').append(tempHtml);
    console.log(tempHtml);
}


function readBoard() {
    $('#table').empty();
    // 2. 메모 목록을 불러와서 HTML로 붙입니다.
    $.ajax({
        type: 'GET',
        url: '/api/boards',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let board = response[i];
                let id = board.id;
                let username = board.username;
                let title = board.title;
                let modifiedAt = board.modifiedAt;
                addHTML(id, username,title, modifiedAt);
            }
        }
    })
}

function addHTML(id, username, title, modifiedAt) {
    // 1. HTML 태그를 만듭니다.
    let tempHtml = `<table class="table" id="table">
                                <tbody>
                                <tr>
                                    <th scope="row">${id}</th>
                                    <td onclick="detailBoard(${id})">${username}</td>
                                    <td>${title}</td>
                                    <td>${modifiedAt}</td>
                                </tr>
                                </tbody>
                        </table>`;
    // 2. #cards-box 에 HTML을 붙인다.
    $('#table').append(tempHtml);
}


function writePost() {
    // 1. 작성한 메모를 불러옵니다.
    let contents = $('#contents').val();
    let title = $('#title').val();
    let username = $('#username').val();
    if (title == '') {
        alert("제목을 입력하세요!");
        $('#title').focus();
        return false;
    }
    if (username == '') {
        alert("이름을 입력하세요!");
        $('#username').focus();
        return false;
    }
    if (contents == '') {
        alert("내용을 입력하세요!");
        $('#contents').focus();
        return false;
    }
    let data = {'contents': contents, 'title': title, 'username': username};
    // 5. POST /api/memos 에 data를 전달합니다.
    $.ajax({
        type: "POST",
        url: "/api/boards",
        contentType: "application/json", // JSON 형식으로 전달함을 알리기
        data: JSON.stringify(data),
        success: function (response) {
            alert('게시글이 성공적으로 작성되었습니다.');
            window.location.href = "index.html";
        }
    });
}
function isValidContents(comment) {
    if (comment == '') {
        alert('댓글 내용을 입력해주세요');
        return false;
    }
    return true;
}

    //////////////////////////////////댓글
function writeComment(){

    let comment = $('#comment').val();
    let data1 = {'comment' : comment ,"postId" : postid};
    if (isValidContents(comment) == false) {
        return;
    }
    // 5. POST /api/memos 에 data를 전달합니다.
    $.ajax({
        type: "POST",
        url: "/api/comment",
        contentType: "application/json", // JSON 형식으로 전달함을 알리기
        data: JSON.stringify(data1),
        success: function (response) {
            alert('댓글이 성공적으로 작성되었습니다.');
        }
    });
}
function updateComment(id) {
    let commnentid = id;
    let comment = $(`#${id}`).val().trim();
    if (isValidContents(comment) == false) {
        return;
    }
    let data = {'postId': commnentid, 'comment': comment};

    $.ajax({
        type: "PUT",
        url: '/api/updatecomment',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            if(response){
                alert('댓글 변경에 성공하였습니다.');
                window.location.reload();
            }
        }
    });
}
function deleteCommnent(id) {
    if(confirm("댓글을 삭제하시겠습니까?") == true){
    $.ajax({
        type: "DELETE",
        url: `/api/comment/${id}`,
        success: function (response) {
            if(response){
                alert('댓글 삭제에 성공하였습니다.');
                }
            }
        })
    }
}