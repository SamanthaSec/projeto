const $ = (s, r = document) => r.querySelector(s);

async function api(url, metodo = 'GET', corpo) {
  const r = await fetch(url, {
    method: metodo, credentials: 'same-origin',
    headers: { 'Content-Type': 'application/json' },
    body: corpo ? JSON.stringify(corpo) : undefined
  });
  const t = await r.text();
  let d = null;
  try { d = t ? JSON.parse(t) : null; } catch (e) {}
  if (!r.ok) { const e = new Error((d && d.erro) || 'Erro ' + r.status); e.status = r.status; throw e; }
  return d;
}

function toast(msg, erro) {
  const el = document.createElement('div');
  el.className = 'toast' + (erro ? ' er' : '');
  el.textContent = msg;
  document.body.appendChild(el);
  setTimeout(() => el.remove(), 2800);
}


function mostrar(el, ligado, tipo) { el.style.display = ligado ? (tipo || 'grid') : 'none'; }

const brl = n => n.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
const esc = s => String(s == null ? '' : s).replace(/[&<>"']/g, c => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c]));
