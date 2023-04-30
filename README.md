# BoardPractice2
게시판 2
지난번에 엔터키로 검색이 되었던 이상상황에 대해서 ChatGPT님께서 대답해주셨다....
자세한 사항은 아래를 참조하도록!

궁금한게 있어 나는 게시판 제작에서 검색기능이 붙은 페이지를 제작중이었어
그런데 내가 코드를 끝까지 적지 않아서 게시판 검색기능 버튼을 이렇게 적어두었지
$(function(){
								$('#searchBtn').click(function(){
									self.location = "list" + '${pageMaker.makeQuery(1)}' + "&searchType=" + $("select option:selected").val() });
그런데 이 코드는 사실 잘못되었어, 원래 적어야하는 코드는 이것이야 :
$(function(){
								$('#searchBtn').click(function(){
									self.location = "list" + '${pageMaker.makeQuery(1)}' + "&searchType=" + $("select option:selected").val()
									+ "&keyword=" + encodeURIComponent($('#keywordInput').val());
								});
							});
그런데 문제가 있어. 코드를 처음에 잘못 적었을때는 분명 제대로 작동되면 안되고 실제로 버튼을 클릭해서 작동하면 검색기능이 제대로 작동하지 않았어.
그런데 아이러니한건 버튼을 눌러서 작동할때는 검색이 되지 않았지만 엔터키를 눌러서 검색을 하면 정상적으로 검색이 된다는거야. 도대체 왜 그런것일까?

ChatGPT
잘못된 코드를 작성한 결과는, 검색어를 인코딩하지 않고 전달하는 문제가 있습니다. 이로 인해, 검색어에 공백이나 특수문자 등이 포함되면 검색 기능이 작동하지 않습니다.

하지만 버튼 클릭 대신에 엔터키를 눌러 검색하는 경우에는, 브라우저가 자동으로 form 태그의 submit 기능을 실행하기 때문에 검색어가 제대로 인코딩되어 전달됩니다.

따라서, 제대로 작동하려면 검색어를 인코딩하여 전달해야 합니다. 이를 위해, 검색어를 encodeURIComponent() 함수로 인코딩하여 URL에 전달하도록 수정한 코드가 필요합니다.


User
엔터키를 눌러서 검색이 된다니 신기하네. 그렇다면 그런 경우에는 
$(function(){
								$('#searchBtn').click(function(){
									self.location = "list" + '${pageMaker.makeQuery(1)}' + "&searchType=" + $("select option:selected").val()
									+ "&keyword=" + encodeURIComponent($('#keywordInput').val());
								});
							});
이 함수 자체가 아예 없어도 검색이 실행된다는 말이야????
ChatGPT
엔터키를 눌러 검색하는 경우, 브라우저는 일반적으로 HTML form 태그의 submit 기능을 실행합니다. 이 때, form 태그의 action 속성에 지정된 URL로 데이터를 전송하며, method 속성에 지정된 HTTP 요청 방식으로 데이터를 서버에 전송합니다.

따라서, 검색어를 입력하는 input 태그를 form 태그로 감싸고, form 태그의 action 속성과 method 속성을 설정하여 검색어를 서버로 전송하도록 해야 합니다.

만약 검색어를 입력하는 input 태그를 form 태그로 감싸지 않은 경우, 엔터키를 눌러도 검색이 되지 않을 수 있습니다. 이 경우에는 자바스크립트를 사용하여 엔터키 입력 이벤트를 감지하고, 검색 기능을 실행하는 함수를 호출해야 합니다.

User

2 / 2

그런데 이상해
<select name="searchType">
							<option value="n"<c:out value="${scri.searchType == null ? 'selected' : '' }"/>>-----</option>
							<option value="t"<c:out value="${scri.searchType eq 't' ? 'selected' : '' }"/>>제목</option>
							<option value="c"<c:out value="${scri.searchType eq 'c' ? 'selected' : '' }"/>>내용</option>
							<option value="w"<c:out value="${scri.searchType eq 'w' ? 'selected' : '' }"/>>작성자</option>
							<option value="tc"<c:out value="${scri.searchType eq 'tc' ? 'selected' : '' }"/>>제목+내용</option>
						</select>
나는 검색의 조건을 검색하는 이런 옴션값이 있어.
엔터키로 검색을 한다면 따로 복잡한 커리지정을 해 주지 않아도 알아서 검색이 시행된다는 말이야? 이해할수가 없군
ChatGPT

2 / 2

검색어 입력 폼이 form 태그로 감싸져 있어야만 엔터키로 검색을 실행할 수 있으며, 검색 조건을 선택하는 select 태그는 해당되지 않습니다.

따라서, 검색 조건을 선택하는 select 태그에서 값을 선택하고 검색어 입력 폼에서 검색어를 입력한 뒤, 검색 버튼을 클릭하거나 엔터키를 누르면 검색이 실행됩니다.

검색어 입력 폼과 검색 조건을 선택하는 select 태그가 모두 form 태그로 감싸져 있다면, form 태그에 action 속성과 method 속성을 지정하여 검색 기능을 구현할 수 있습니다. 이 경우, 검색 버튼을 클릭하거나 엔터키를 눌러도 모두 검색이 실행됩니다.

User
확실히 엔터키를 눌러서 이동하는 페이지와 버튼을 눌러서 이동하는 페이지의 url 주소가 다르군. 그럼에도 불구하고 출력되는 게시판의 목록이 같다는게 이상해
ChatGPT
검색 결과가 같은 이유는 검색 결과 페이지의 기능 구현 방식에 따라 다릅니다.

예를 들어, 검색 결과 페이지에서 검색 조건과 검색어를 받아와서 서버에서 검색 쿼리를 생성하고 DB에서 검색을 수행한 뒤, 검색 결과를 출력하는 방식으로 구현한다면, 검색 결과 페이지의 URL이 엔터키로 검색할 때와 버튼으로 검색할 때 다르더라도 검색 결과는 같게 나타날 수 있습니다.

또한, 검색 결과 페이지에서 URL을 사용하지 않고, Ajax 등의 기술을 이용하여 동적으로 검색 결과를 출력하는 방식으로 구현한다면, 검색 결과 페이지의 URL이 달라도 검색 결과는 같게 나타날 수 있습니다.

즉, URL이 달라도 검색 결과가 같게 나타나는 이유는 검색 결과를 출력하는 방식에 따라 다르기 때문입니다.

User
http://localhost:8080/board/list?page=1&perPageNum=10&searchType=t&keyword=%E3%84%B4%E3%85%87%E3%85%80
검색결과가 있는 게시판의 url 주소는 위와 같아. 여기서 ?와 & 등의 차이는 무엇이지?
ChatGPT
URL은 웹 서버에 저장된 자원을 찾아가기 위한 주소이며, URL에서의 ?와 &는 쿼리 스트링(query string)의 구분자입니다.

쿼리 스트링은 URL에 추가되는 key-value 쌍의 집합이며, 서버로 전달되어 요청된 자원에 대한 추가적인 정보를 제공합니다. ?는 URL의 기본 주소와 쿼리 스트링을 구분하는 역할을 합니다. 예를 들어, http://example.com/search?q=apple에서는 http://example.com/search가 기본 주소이며, q=apple이라는 쿼리 스트링이 추가됩니다.

&는 여러 개의 key-value 쌍을 구분하는 역할을 합니다. 예를 들어, http://example.com/search?q=apple&lang=en에서는 q=apple과 lang=en이라는 두 개의 key-value 쌍이 쿼리 스트링에 포함되어 있습니다.

따라서, 위의 URL에서 ? 다음에 오는 부분은 페이지 번호와 페이지당 게시글 수를 나타내는 key-value 쌍이며, & 다음에 오는 부분은 검색 조건과 검색어를 나타내는 key-value 쌍입니다.


User
그렇다면 url 주소에는 ?는 하나가 나오겠군. 일단 기본주소와 쿼리스트링을 구분하는 역할을 위해서 쓰는것 말이야. 그리고 &는 key 값과 같은 양만큼 있어야겠고
ChatGPT
네, 맞습니다. URL 주소에는 기본적으로 ? 기호가 하나만 사용되며, 이후에 쿼리 스트링의 키-값 쌍이 & 기호로 구분되어 나열됩니다. 따라서 쿼리 스트링의 값에 & 기호가 포함된 경우, 이를 인코딩하여 %26과 같은 형태로 URL에 적어줘야 합니다. 이와 같이 쿼리 스트링을 구성함으로써, 서버는 클라이언트의 요청에 따라 동적으로 콘텐츠를 생성하거나 필요한 데이터를 전달할 수 있습니다.


User
문득 궁금해지는데 url 주소의 최대 길이는 있어? key=value 쌍도 최대 한도갯수가 있어?
ChatGPT
네, URL의 최대 길이는 브라우저마다 다를 수 있지만 대체로 2,048~8,192자 정도입니다. 이는 웹 서버, 브라우저, 프로토콜 등에 따라 다를 수 있습니다. 또한, HTTP 프로토콜의 특성상 GET 요청에서는 URL에 전송할 수 있는 데이터의 크기가 제한되어 있기 때문에 URL에 너무 많은 데이터를 담을 수는 없습니다.

또한, key=value 쌍의 수도 브라우저마다 다르며, 대개는 몇 백개에서 몇 천개 사이로 제한됩니다. 이 수는 브라우저의 제한뿐만 아니라, 웹 서버에서도 설정할 수 있는데, 이를 통해 보안 상 문제가 될 수 있는 큰 쿼리스트링을 막을 수 있습니다.

따라서, URL의 최대 길이나 key=value 쌍의 수 등은 너무 많은 데이터를 URL에 담는 것을 방지하기 위해 제한을 두고 있습니다. 이러한 제한을 고려하여 웹 애플리케이션을 설계해야 합니다.


User
나는 시험을 위해서 엔터키를 이용해서 http://localhost:8080/board/list?searchType=t&keyword=111
페이지로 이동했어. 이런 페이지로 이동하면 나중에 문제가 될까?? 버튼을 눌러서 가는 페이지
http://localhost:8080/board/list?page=1&perPageNum=10&searchType=t&keyword=111 와는 엄연히 다르잖아?
ChatGPT
엔터키를 이용해서 http://localhost:8080/board/list?searchType=t&keyword=111 페이지로 이동하는 것과 버튼을 이용해서 http://localhost:8080/board/list?page=1&perPageNum=10&searchType=t&keyword=111 페이지로 이동하는 것은 URL이 조금씩 다르긴 하지만, 검색 조건에 따라서 출력되는 게시글 목록은 동일하게 나와야 합니다.

그러므로, 검색 조건에 맞는 게시글 목록이 올바르게 출력된다면, 두 가지 방법으로 이동하는 것에는 문제가 없습니다. 하지만, 검색 조건에 따라서 다른 게시글 목록이 출력된다면, URL에 누락된 파라미터나 잘못된 파라미터가 있을 수 있으므로, 다시 한 번 파라미터를 확인해 보시는 것이 좋습니다.
