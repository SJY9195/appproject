import 'package:dio/dio.dart';

class ExpenseModel {
  final dio = Dio();
  final String baseUrl = 'http://localhost:8082/expenses';

  Future<void> createExpense(Map<String, dynamic> expenseData) async {
    try {
      final response = await dio.post('$baseUrl/insert', data: expenseData);
          if (response.statusCode == 200) {
            print('비용이 성공적으로 추가되었습니다.');
          } else {
            throw Exception('비용 추가 실패');
          }
        } catch (e) {
          print('Error: $e');
          throw Exception('Error: $e');
        }
      }

  Future<List<dynamic>> getExpenses() async {
    try {
      final response = await dio.get(baseUrl);
      if (response.statusCode == 200) {
        return response.data as List<dynamic>;
      } else {
        throw Exception('비용 목록 불러오기 실패');
      }
    } catch (e) {
      print('Error: $e');
      throw Exception('Error: $e');
    }
  }

  Future<Map<String, dynamic>> getExpensesGroupedByDays(int tripId) async {
    try {
      final response = await dio.get('$baseUrl/grouped-by-days/$tripId');
      if (response.statusCode == 200) {
        return response.data as Map<String, dynamic>;
      } else {
        throw Exception('기간별 비용 조회 실패');
      }
    } catch(e) {
      print('Error: $e');
      throw Exception('Error: $e');
    }
  }
}