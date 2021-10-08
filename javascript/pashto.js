//----------------------------------------------------------------
// Helper Functions
//----------------------------------------------------------------

function switch_css_to(name) {
  document.getElementById('curr_css').setAttribute("href", "pashto/static/style/" + name);
}

function createDiv(class_ ='', id ='', value='', innerHTML=''){
  let element = document.createElement('div');
  element.setAttribute('class', class_);
  element.setAttribute('id', id);
  element.setAttribute('value', value);
  element.innerHTML=innerHTML;
  return element;
}

function get_and_clone(element) {
    let node = document.getElementById(element).cloneNode(true);
    return node;
}

function clear(element) {
  let node = document.getElementById(element);
  while (node.firstChild) {
     node.removeChild(node.firstChild);
   }
   node = get_and_clone(element);
}

function add_to_container(element) {
  document.getElementById('container').appendChild(element);
}


function create_back_button(){
  let back = createDiv('back', 'back', '', "<img src='pashto/static/images/back.svg' />");
  return back;
}

//----------------------------------------------------------------


const main_container = document.body.innerHTML;
const search_area_clone = get_and_clone('search_area');
const submit_data_form_clone = get_and_clone('submit_data_form');






/*-----------------------------------------------------------------------

Home Page 

------------------------------------------------------------------------*/
function set_home_page() {
  /*First View*/
  clear('container');
  switch_css_to('main.css');
  document.body.innerHTML = main_container;
  document.getElementById("input_area").addEventListener("click",set_search_page);
  document.getElementById("search_area").addEventListener("submit",function() {return false;});
  if (window.screen.width>700){
    let input_area = document.getElementById("input_area");
    input_area.setAttribute('placeholder', ' Try parrdah or پړده');
  }
}





/*-----------------------------------------------------------------------

Search Page 

------------------------------------------------------------------------*/


function set_search_page() {
let screenWidth = window.screen.width;
if (screenWidth > 700) {
  set_desktop_search_page();
}
else {
  set_mobile_search_page();
  let input_area = document.getElementById("input_area");
  input_area.setAttribute('placeholder', ' Try parrdah or پړده');
}

}

function set_desktop_search_page(){
/*Second View for screens greather than 700px"""*/
  set_home_page();
  add_search_area_clone();

  if (document.getElementById('suggestions_box')) {
      clear('suggestions_box');
    }
  add_input_area_listeners();
  document.getElementById("input_area").focus();
  

}

function set_mobile_search_page() {
  /*Second View for screens less than 700px*/
  clear('container');
  switch_css_to('search.css');

  /*Add back button */
  let back = create_back_button();
  back.addEventListener('click', set_home_page);
  add_to_container(back);


  add_search_area_clone();
  
  add_input_area_listeners();
  document.getElementById("input_area").focus();

}

function add_search_area_clone() {
  let search_area = document.getElementById('search_area');
  let submit_data_form = document.getElementById('submit_data_form');
  if (search_area==null) {
    document.getElementById('container').appendChild(search_area_clone);
  }
  if (submit_data_form==null) {
    document.getElementById('container').appendChild(submit_data_form_clone);
  }

}

function add_input_area_listeners(){
  let input_area = document.getElementById("input_area");

   input_area.addEventListener('input',  async function() {
    if (document.getElementById('suggestions_box') != null) {
      document.getElementById('suggestions_box').remove();
    }
    let suggestions_box = createDiv('suggestions_box', 'suggestions_box');
    document.getElementById('container').appendChild(suggestions_box);
    
    let entry = input_area.value.toLowerCase();
     await get_suggestions(entry);
  })
  input_area.addEventListener('click', function() {

    input_area.value = '';
    
  })
}



async function get_suggestions(query) {
  //data format {'query': search query}
 let data = {"query": query};
 if (data['query']){
  const response = fetch('update_suggestions', {
    method: 'POST',headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(query)
 }).then(raw_data_to => {
  raw_data_to.json()
  .then(add_suggestions_box);
})}
}

async function add_suggestions_box(data) {
  
  for (i = 0; i < data.length; i++){
    if (data[i]['pashto'] && document.getElementById('suggestions_box')) {
      let curr = data[i];

      let suggestion_innerHTML = "<i>"+curr['phonetic']+"</i>" + "      " + 
                                  "<b>" + curr['pashto'] + "</b>"+ "<br>" + 
                                  "<div class='inline_def'>"+curr['meaning'].slice(0, 20)+"....</div>";
  

      let suggestion = createDiv(class_='suggestion',id='suggestion', value=JSON.stringify(curr), innerHTML=suggestion_innerHTML);
      document.getElementById('suggestions_box').append(suggestion);
    } 
  }

  if (document.getElementById('suggestions_box')) {
  let suggestions_list = document.getElementById('suggestions_box').childNodes;
  
  suggestions_list.forEach( function(element) {

    element.addEventListener('click', function() {
            document.getElementById('submit_data').value = element.getAttribute('value');

      document.getElementById('submit_data_form').submit();
    })
  })
}
}






/*-----------------------------------------------------------------------

Start  

------------------------------------------------------------------------*/


set_home_page();









