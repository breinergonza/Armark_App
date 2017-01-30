/**
 * Copyright 2015 Red Hat, Inc., and individual contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feedhenry.armark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;
import com.feedhenry.sdk.api.FHCloudRequest;

import org.json.fh.JSONArray;
import org.json.fh.JSONObject;

public class HelloFragment extends Fragment {

    private static final String TAG = "";//InitFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = View.inflate(getActivity(), R.layout.hello_fragment, null);
        final TextView responseTextView = (TextView) view.findViewById(R.id.cloud_response);

        Button BtnUsuario = (Button) view.findViewById(R.id.BtnUsuario);
        BtnUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                responseTextView.setText("");
                v.setEnabled(false);
                cloudCallUsuario(v, responseTextView);
            }
        });

        //**********************

        Button BtnAlmacenes = (Button) view.findViewById(R.id.BtnAlmacenes);
        BtnAlmacenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                responseTextView.setText("");
                v.setEnabled(false);
                cloudCallAlmacenes(v, responseTextView);
            }
        });

        //**********************

        Button BtnCategorias = (Button) view.findViewById(R.id.BtnCategorias);
        BtnCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                responseTextView.setText("");
                v.setEnabled(false);
                cloudCallCategorias(v, responseTextView);
            }
        });

        //**********************

        Button BtnPromociones = (Button) view.findViewById(R.id.BtnPromociones);
        BtnPromociones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                responseTextView.setText("");
                v.setEnabled(false);
                cloudCallPromociones(v, responseTextView);
            }
        });

        //**********************
        Button BtnLogin = (Button) view.findViewById(R.id.BtnLogin);
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                responseTextView.setText("");
                v.setEnabled(false);

                Intent myIntent = new Intent(view.getContext(), Loggin.class);
                startActivityForResult(myIntent, 0);

            }
        });

        return view;
    }

    public void cloudCallUsuario(final View v, final TextView responseTextView) {
        try {
            JSONObject params = new JSONObject("{correo: 'breinergonza@hotmail.com', password: '123456' }");

            FHCloudRequest request = FH.buildCloudRequest("login", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - success");
                    v.setEnabled(true);
                    responseTextView.setText(fhResponse.getJson().toString());
                }

                @Override
                public void fail(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - fail");
                    Log.e(TAG, fhResponse.getErrorMessage(), fhResponse.getError());
                    v.setEnabled(true);
                    responseTextView.setText(fhResponse.getErrorMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e.getCause());
        }
    }

    private void cloudCallAlmacenes(final View v, final TextView responseTextView) {
        try {
            JSONObject params = new JSONObject("{fecha: '2015-09-11' }");

            FHCloudRequest request = FH.buildCloudRequest("almacenes", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - success");
                    v.setEnabled(true);
                    //responseTextView.setText(fhResponse.getJson().toString());

                    int Id;
                    String RazonSocial;
                    String resp = "";

                    JSONArray array = fhResponse.getArray();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
                        Id = row.getInt("Id");
                        RazonSocial = row.getString("RazonSocial");

                        resp += "Id : " + Id + ", Razon Social : " + RazonSocial + "\n";
                    }

                    responseTextView.setText(resp);

                }

                @Override
                public void fail(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - fail");
                    Log.e(TAG, fhResponse.getErrorMessage(), fhResponse.getError());
                    v.setEnabled(true);
                    responseTextView.setText(fhResponse.getErrorMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e.getCause());
        }
    }

    private void cloudCallCategorias(final View v, final TextView responseTextView) {
        try {
            JSONObject params = new JSONObject("{fecha: '2015-09-11' }");

            FHCloudRequest request = FH.buildCloudRequest("categorias", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - success");
                    v.setEnabled(true);
                    //responseTextView.setText(fhResponse.getJson().toString());

                    int Id;
                    String Nombre;
                    String resp = "";

                    JSONArray array = fhResponse.getArray();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
                        Id = row.getInt("Id");
                        Nombre = row.getString("Nombre");

                        resp += "Id : " + Id + ", Nombre : " + Nombre + "\n";
                    }

                    responseTextView.setText(resp);

                }

                @Override
                public void fail(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - fail");
                    Log.e(TAG, fhResponse.getErrorMessage(), fhResponse.getError());
                    v.setEnabled(true);
                    responseTextView.setText(fhResponse.getErrorMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e.getCause());
        }
    }

    private void cloudCallPromociones(final View v, final TextView responseTextView) {
        try {
            JSONObject params = new JSONObject("{fecha: '2015-09-11' }");

            FHCloudRequest request = FH.buildCloudRequest("promociones", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - success");
                    v.setEnabled(true);
                   // responseTextView.setText(fhResponse.getJson().toString());

                    int Id;
                    String Descripcion;
                    String resp = "";

                    JSONArray array = fhResponse.getArray();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
                        Id = row.getInt("Id");
                        Descripcion = row.getString("Descripcion");

                        resp += "Id : " + Id + ", Descripcion : " + Descripcion + "\n";
                    }

                    responseTextView.setText(resp);

                }

                @Override
                public void fail(FHResponse fhResponse) {
                    Log.d(TAG, "cloudCall - fail");
                    Log.e(TAG, fhResponse.getErrorMessage(), fhResponse.getError());
                    v.setEnabled(true);
                    responseTextView.setText(fhResponse.getErrorMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e.getCause());
        }
    }

}
