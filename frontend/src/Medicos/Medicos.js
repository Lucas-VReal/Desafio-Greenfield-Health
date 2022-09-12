function Medicos({botao}){
    return (
        <form>
            <input type='text' placeholder='CPF' className='form-control'/>
            <input type='text' placeholder='Nome' className='form-control' />
            <input type='text' placeholder='E-mail' className='form-control'/>
            <input type='text' placeholder='Data de Nascimento' className='form-control'/>
            <input type='text' placeholder='Sexo' className='form-control'/>
            <input type='text' placeholder='CRM' className='form-control'/>
            <input type='boolean' placeholder='Estado CRM' className='form-control'/>

            {
                botao
                ?
                <input type='button' value= 'Cadastrar' className='btn btn-primary'/>
                :
                <div>
                    <input type='button' value='Atualizar' className='btn btn-warning'/>
                    <input type='button' value='Remover' className='btn btn-danger'/>
                    <input type='button' value='Cancelar' className='btn btn-secondary'/>
                </div>


            }

            
        </form>
    )
}

export default Medicos;