<#import "blocks/template.ftlh" as t>
<@t.template user>
<h1 style="text-align: center">Мои товары</h1>
<hr>
<a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
   data-toggle="collapse" href="#collapseExample" role="button"
   aria-expanded="true" aria-controls="collapseExample">
    Добавить новый товар
</a>
<div class="collapse mb-3" id="collapseExample" style="">
    <form action="/product/create" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="productName">Название товара</label>
            <input type="text" required class="form-control"
                   id="productName" name="title">
        </div>
        <div class="form-group">
            <label for="price">Цена товара</label>
            <div class="input-group mb-3">
                <input type="number" required class="form-control" id="price" name="price">
                <div class="input-group-append">
                    <span class="input-group-text">руб.</span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="city">Город</label>
            <select id="city" name="city" class="form-control" required>
                <option value="">Город не выбран</option>
                <option value="Гродно">Гродно</option>
                <option value="Минск">Минск</option>
                <option value="Витебск">Витебск</option>
                <option value="Брест">Брест</option>
                <option value="Гомель">Гомель</option>
            </select>
        </div>
        <div class="form-group">
            <label for="description">Описание товара</label>
            <textarea required class="form-control" id="description" name="description"></textarea>
        </div>
        <div class="form-group">
            <div class="custom-file">
                <input required type="file" name="file1" id="file1">
                <label class="custom-file-label" for="file1">Первая фотография к товару</label>
            </div>
        </div>
        <div class="form-group">
            <div class="custom-file">
                <input type="file" name="file2" id="file2">
                <label class="custom-file-label" for="file2">Вторая фотография к товару</label>
            </div>
        </div>
        <div class="form-group">
            <div class="custom-file">
                <input type="file" name="file3" id="file3">
                <label class="custom-file-label" for="file3">Третья фотография к товару</label>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <button type="submit" style="width: 100%" class="btn btn-dark">Добавить</button>
    </form>
</div>
<div class="card-columns">
    <#list products as product>
    <a href="/product/${product.id}" class="product-link">
        <div class="card text-white bg-dark my-3 product">
            <div class="card-top">
                <form action="/product/delete/${product.id}" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-danger m-3">
                        Удалить
                    </button>
                </form>
            </div>
            <img src="/images/${product.previewImageId}"
                 class="card-img-top">
            <div class="card-body">
                <h5 class="card-title">${product.title}</h5>
                <p class="card-text">${product.price} ₽</p>
            </div>
            <div class="card-footer">
                ${product.city}
            </div>
        </div>
    </a>
    <#else>
    <h1 style="text-align: center">Не найдено</h1>
</#list>
</div>

<script>
  // Получаем все элементы input типа file
  const fileInputs = document.querySelectorAll('input[type="file"]');

  // Перебираем каждый элемент input
  fileInputs.forEach(fileInput => {
    const fileLabel = fileInput.nextElementSibling;

    // Добавляем обработчик события change
    fileInput.addEventListener('change', function() {
      const files = Array.from(this.files);
      const fileNames = files.map(file => file.name);

      // Обновляем текстовое содержимое метки с именем файла
      fileLabel.textContent = fileNames.join(', ');
    });
  });
</script>
</@t.template>