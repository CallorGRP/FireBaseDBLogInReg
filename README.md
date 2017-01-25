# FireBase를 이용해 간단히 등록, LogIn하기


 private DatabaseReference databaseReference;
databaseReference = FirebaseDatabase.getInstance().getReference("users");
//users 라는 키를 가진 값들을 참조한다.
...
		//로그인 영역
		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
						//users의 모든 자식들의 key값과 value 값들을 iterator로 참조합니다.
                        while(child.hasNext())
                        {
							//찾고자 하는 ID값은 key로 존재하는 값
                            if(child.next().getKey().equals(checkId.getText().toString()))
                            {
                                Toast.makeText(getApplicationContext(),"로그인!",Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                        Toast.makeText(getApplicationContext(),"존재하지 않는 아이디입니다.",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
...
//아이디 등록 영역
private ValueEventListener checkRegister = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
            while (child.hasNext()) {//마찬가지로 중복 유무 확인
                if (editEmail.getText().toString().equals(child.next().getKey())) {
                    Toast.makeText(getApplicationContext(), "존재하는 아이디 입니다.", Toast.LENGTH_LONG).show();
                    databaseReference.removeEventListener(this);
                    return;
                }
            }
            makeNewId();
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };
...
void makeNewId()
    {
        Date date = new Date(System.currentTimeMillis()); //날짜
        databaseReference.child(editId.getText().toString()).child("가입일").setValue(date.toString());
		//users를 가리키는 기본 참조에서 시작 -> child(Id를 key로 가지는 자식) ->child("가입일 이라는 key를 갖는 자식")의 value를 날짜로 저장
        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
    }
...
