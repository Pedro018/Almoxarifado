// Pré-visualiza o valor total (quantidade x valor unitário) enquanto o usuário digita.
// A regra oficial continua no domínio (Produto.getValorTotal); aqui é só conveniência visual.
(function () {
  var qtd = document.getElementById('quantidade');
  var val = document.getElementById('valorUnitario');
  var tot = document.getElementById('valorTotal');
  if (!qtd || !val || !tot) return;

  var BRL = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' });

  function atualizar() {
    var q = /^\d+$/.test(qtd.value.trim()) ? parseInt(qtd.value.trim(), 10) : NaN;
    var v = val.value.trim().replace(/R\$|\s/g, '');
    if (v.indexOf(',') > -1) v = v.replace(/\./g, '').replace(',', '.');
    var n = /^\d+(\.\d{1,2})?$/.test(v) ? Math.round(parseFloat(v) * 100) : NaN;
    tot.value = BRL.format(isNaN(q) || isNaN(n) ? 0 : (q * n) / 100);
  }

  qtd.addEventListener('input', atualizar);
  val.addEventListener('input', atualizar);
  atualizar();
})();
