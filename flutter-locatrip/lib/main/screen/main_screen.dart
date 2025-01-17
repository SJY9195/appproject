import 'package:flutter/material.dart';
import 'package:flutter_locatrip/trip/screen/first_trip_screen.dart';
import 'package:flutter_locatrip/checklist/screen/checklist_screen.dart';
import 'package:flutter_locatrip/expense/screen/expense_screen.dart';
import 'package:flutter_locatrip/trip/screen/trip_view_screen.dart';

class MainScreen extends StatefulWidget {
  const MainScreen({super.key});

  @override
  State<MainScreen> createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: PreferredSize(
        preferredSize: Size.fromHeight(64),
        child: AppBar(
          title: Text(
            "여행자, 평온한 토미님!",
            style: TextStyle(fontSize: 20),
            // 참고용
            // style: Theme.of(context).textTheme.headlineLarge,
          ),
          actions: [
            IconButton(onPressed: () {}, icon: Icon(Icons.notifications)),
            TextButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => ChecklistScreen(),
                  ),
                );
              },
              child: Text(
                '체크리스트',
                style: TextStyle(color: Colors.black),
              ),
            ),
            TextButton(
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => ExpenseScreen(tripId: 1),
                    ),
                  );
                },
                child: Text(
                  '가계부',
                  style: TextStyle(color: Colors.black),
                ))
          ],
        ),
      ),
      body: Column(
        children: [
          Text("메인"),
          ElevatedButton(
              onPressed: () {
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => TripViewScreen(tripId: 1)));
              },
              child: Text("일정 불러오는지 테스트"))
        ],
      ),
      floatingActionButton: Container(
        width: 68,
        height: 65,
        child: FloatingActionButton(
          onPressed: () {
            Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => TripScreen(),
                  // fullscreenDialog: true,
                ));
          },
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Icon(
                Icons.add,
              ),
              Text(
                "일정생성",
                style: Theme.of(context).textTheme.labelMedium?.copyWith(
                      color: Colors.white,
                    ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
