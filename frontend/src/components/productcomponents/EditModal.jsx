// EditModal.jsx
import { forwardRef, useImperativeHandle, useEffect, useState } from "react";
import { createPortal } from "react-dom";

const API_BASE = "/product";
const UPDATE_URL = "/product/update"; // Nginx will proxy this to the backend

const getModalRoot = () => {
  let el = document.getElementById("modal-root");
  if (!el) {
    el = document.createElement("div");
    el.id = "modal-root";
    document.body.appendChild(el);
  }
  return el;
};

const EditModal = forwardRef(function EditModal({ onSaved }, ref) {
  const [open, setOpen] = useState(false);
  const [saving, setSaving] = useState(false);
  const [draft, setDraft] = useState(null);
  const [originalUPC, setOriginalUPC] = useState(null);
  const modalRoot = getModalRoot();

  useImperativeHandle(ref, () => ({
    open(row) {
      setOpen(true); // open first so overlay appears immediately
      setOriginalUPC(row.upc);
      setDraft({
        upc: row.upc,
        description: row.description ?? "",
        brand: row.brand ?? "",
        department: row.department ?? "",
        category: row.productCategory ?? "",
        packageType: row.packageDescription ?? "",
        baseCost: Number(row.retailPrice ?? 0),
        retailCost: Number(row.retailPrice ?? 0),
        stock: Number(row.currentStock ?? 0),
      });
    },
    close() {
      setOpen(false);
      setDraft(null);
      setOriginalUPC(null);
    },
  }));

  // lock background scroll
  useEffect(() => {
    if (!open) return;
    const prev = document.body.style.overflow;
    document.body.style.overflow = "hidden";
    return () => {
      document.body.style.overflow = prev;
    };
  }, [open]);

  function onChange(field, val) {
    setDraft((d) => ({ ...d, [field]: val }));
  }

  async function save() {
    if (!draft) return;
    setSaving(true);

    try {
      // Build exactly what your API expects.
      // (If your DTO uses different names, change them here.)
      const payload = {
        upc: String(draft.upc).trim(),
        description: draft.description?.trim() ?? "",
        brand: draft.brand?.trim() ?? "",
        department: draft.department?.trim() ?? "",
        productCategory: draft.category?.trim() ?? "",
        packageDescription: draft.packageType?.trim() ?? "",
        costOfGood: Number(draft.baseCost ?? 0),
        retailPrice: Number(draft.retailCost ?? 0),
        currentStock: Number(draft.stock ?? 0),
      };
      console.log("Saving product:", payload);

      const res = await fetch(UPDATE_URL, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      // Some backends return 204 No Content; some return the updated product.
      if (!res.ok) {
        const text = await res.text();
        console.error("SAVE FAILED:", res.status, text, payload);
        throw new Error(text || `HTTP ${res.status}`);
      }

      let updated = payload;
      try {
        // if server returns JSON, prefer it
        updated = await res.json();
      } catch {
        /* no body (204) — fall back to our payload */
      }

      // tell parent to update the table (oldUPC lets parent replace the right row)
      onSaved?.(updated, originalUPC ?? payload.upc);

      // close + reset
      setOpen(false);
      setDraft(null);
      setOriginalUPC(null);
    } catch (e) {
      alert(e.message || "Could not save");
    } finally {
      setSaving(false);
    }
  }

  if (!open) return null;

  // PORTAL → guaranteed overlay (position:fixed on viewport)
  return createPortal(
    <div style={{ position: "fixed", inset: 0, zIndex: 100000 }}>
      {/* backdrop */}
      <div
        style={{ position: "absolute", inset: 0, background: "rgba(0,0,0,.5)" }}
        onClick={() => setOpen(false)}
      />
      {/* centered dialog */}
      <div
        style={{
          position: "absolute",
          inset: 0,
          display: "grid",
          placeItems: "center",
          padding: "1rem",
        }}
      >
        <div
          className="rounded-2xl bg-white shadow-xl"
          style={{ width: "100%", maxWidth: 672 }}
          onClick={(e) => e.stopPropagation()}
        >
          {/* header */}
          <div className="flex items-center justify-between px-5 py-4 border-b">
            <h2 className="text-lg font-semibold">
              Edit Product —{" "}
              <span className="text-gray-600">{originalUPC ?? ""}</span>
            </h2>
            <button
              className="rounded-md px-3 py-1.5 hover:bg-gray-100"
              onClick={() => setOpen(false)}
            >
              Close
            </button>
          </div>

          {/* body (show a loader while draft hydrates) */}
          {!draft ? (
            <div className="px-5 py-10 text-center text-gray-500">Loading…</div>
          ) : (
            <div className="px-5 py-4 grid grid-cols-1 md:grid-cols-2 gap-4">
              {/* your inputs — keep exactly as you had, just using draft?.field */}
              {/* Example: */}
              {console.log(draft)}
              <Field label="Description">
                <input
                  className="w-full rounded-lg border px-3 py-2"
                  value={draft.description}
                  onChange={(e) => onChange("description", e.target.value)}
                />
              </Field>
              <Field label="Brand">
                <input
                  className="w-full rounded-lg border px-3 py-2"
                  value={draft.brand}
                  onChange={(e) => onChange("brand", e.target.value)}
                />
              </Field>
              <Field label="Depatment">
                <input
                  className="w-full rounded-lg border px-3 py-2"
                  value={draft.department}
                  onChange={(e) => onChange("department", e.target.value)}
                />
              </Field>
              <Field label="Category">
                <input
                  className="w-full rounded-lg border px-3 py-2"
                  value={draft.category}
                  onChange={(e) => onChange("category", e.target.value)}
                />
              </Field>
              <Field label="PackageType">
                <input
                  className="w-full rounded-lg border px-3 py-2"
                  value={draft.packageType}
                  onChange={(e) => onChange("packageType", e.target.value)}
                />
              </Field>
              <Field label="Base Cost">
                <input
                  className="w-full rounded-lg border px-3 py-2"
                  value={draft.baseCost}
                  onChange={(e) => onChange("baseCost", e.target.value)}
                />
              </Field>
              <Field label="Retail Cost">
                <input
                  className="w-full rounded-lg border px-3 py-2"
                  value={draft.retailCost}
                  onChange={(e) => onChange("retailCost", e.target.value)}
                />
              </Field>
              <Field label="Stock">
                <input
                  className="w-full rounded-lg border px-3 py-2"
                  value={draft.stock}
                  onChange={(e) => onChange("stock", e.target.value)}
                />
              </Field>

              {/* ...other fields... */}
            </div>
          )}

          {/* footer */}
          <div className="px-5 py-4 border-t flex items-center justify-end gap-2">
            <button
              className="rounded-lg px-4 py-2 border hover:bg-gray-50"
              onClick={() => setOpen(false)}
              disabled={saving}
            >
              Cancel
            </button>
            <button
              className="rounded-lg px-4 py-2 bg-indigo-600 text-white hover:bg-indigo-700 disabled:opacity-60"
              onClick={save}
              disabled={saving || !draft}
            >
              {saving ? "Saving..." : "Save changes"}
            </button>
          </div>
        </div>
      </div>
    </div>,
    modalRoot
  );
});

export default EditModal;

function Field({ label, children }) {
  return (
    <label className="text-sm">
      <div className="mb-1 text-gray-600">{label}</div>
      {children}
    </label>
  );
}
