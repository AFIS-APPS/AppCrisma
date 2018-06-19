public class RcAvisoAdapter extends RecyclerView.Adapter<RcAvisoAdapter.ViewHolder> {

    private List<Turmas> turmas;
    private Context context;
    private Boolean[] aux;

    public RcAvisoAdapter(List<Turmas> turmas, Context context) {
        this.turmas = new ArrayList<>(turmas);
        this.context = context;
        aux = new Boolean[turmas.size()];
    }

    @Override
    public RcAvisoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_turmas, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RcAvisoAdapter.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;

        Turmas modeloTurma =  turmas.get(position);

        viewHolder.nome.setText(turma.getNomeCrismando().toUpperCase());
        viewHolder.nfaltas.setText(String.valueOf(turma.getNumFaltas()));
        viewHolder.chkPresente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            count = getPosition(turma);
                            frequencia[count].setPresente(true);
                            salvarFirebase(frequencia[count], String.valueOf(currentData.get(Calendar.YEAR)), turma);
                            chkFaltou.setChecked(false);

                        }
                    }
                });

        viewHolder.chkFaltou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    count = getPosition(turma);
                    frequencia[count].setPresente(false);
                    salvarFirebase(frequencia[count], String.valueOf(currentData.get(Calendar.YEAR)), turma);
                    chkPresente.setChecked(false);
                }
            }
        });
            
        final int pos = position;

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.corpo.getVisibility() != View.VISIBLE){
                    viewHolder.corpo.setVisibility(View.VISIBLE);
                    //btExpand.setImageDrawable(R.drawable.);

                }else{
                    viewHolder.corpo.setVisibility(View.GONE);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return turmas.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setTurmas(List<Turmas> turmas) {
        this.turmas = turmas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView nome, nfaltas;
        public final Checkbox chkPresente, chkFaltou;

        public ViewHolder(View view) {
            super(view);
            nome = view.findViewById(R.id.LstNome);
            nfaltas = view.findViewById(R.id.LstFaltas);
            chkPresente = view.findViewById(R.id.checkBoxPresente);
            chkFaltou = view.findViewById(R.id.checkBoxFaltou); 
        }
    }
}